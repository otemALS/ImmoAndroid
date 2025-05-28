package bts.sio.azurimmo.views.paiement


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bts.sio.azurimmo.model.Paiement
import bts.sio.azurimmo.viewmodel.PaiementViewModel

@Composable
fun PaiementList(
    navController: NavHostController,
    viewModel: PaiementViewModel = viewModel(),
    onAddClick: () -> Unit
) {
    val paiements = viewModel.paiements.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    var selectedPaiement by remember { mutableStateOf<Paiement?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getPaiements()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            errorMessage != null -> Text(errorMessage, Modifier.align(Alignment.Center))
            else -> {
                Column {
                    FloatingActionButton(
                        onClick = onAddClick,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Ajouter un paiement")
                    }

                    LazyColumn {
                        items(paiements) { paiement ->
                            PaiementCard(paiement = paiement) {
                                selectedPaiement = it
                                showDialog = true
                            }
                        }
                    }

                    if (showDialog && selectedPaiement != null) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Paiement ${selectedPaiement?.id}") },
                            text = { Text("Voulez-vous modifier ou supprimer ce paiement ?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    selectedPaiement?.id?.let {
                                        navController.navigate("edit_paiement/$it")
                                    }
                                    showDialog = false
                                }) {
                                    Text("Modifier")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    selectedPaiement?.id?.let { id ->
                                        viewModel.deletePaiement(id) {
                                            viewModel.getPaiements()
                                        }
                                    }
                                    showDialog = false
                                }) {
                                    Text("Supprimer")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
