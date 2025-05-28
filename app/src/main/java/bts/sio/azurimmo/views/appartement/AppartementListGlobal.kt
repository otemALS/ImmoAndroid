package bts.sio.azurimmo.views.appartement

import AppartementViewModel
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
import bts.sio.azurimmo.model.Appartement

@Composable
fun AppartementListGlobal(
    navController: NavHostController,
    viewModel: AppartementViewModel = viewModel(),
    onAddClick: () -> Unit
) {
    val appartements = viewModel.appartements.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    var selectedAppartement by remember { mutableStateOf<Appartement?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAppartements()
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
                        Icon(Icons.Default.Add, contentDescription = "Ajouter un appartement")
                    }

                    LazyColumn {
                        items(appartements) { appartement ->
                            AppartementCard(
                                appartement = appartement,
                                onClick = {
                                    selectedAppartement = it
                                    showDialog = true
                                }
                            )
                        }
                    }

                    if (showDialog && selectedAppartement != null) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Action sur l'appartement ${selectedAppartement?.numero}") },
                            text = { Text("Voulez-vous modifier ou supprimer cet appartement ?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    selectedAppartement?.let {
                                        navController.navigate("edit_appartement/${it.id}")
                                    }
                                    showDialog = false
                                }) {
                                    Text("Modifier")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    selectedAppartement?.id?.let { id ->
                                        viewModel.deleteAppartement(id) {
                                            println(">>> Supprimé appartement : $id")
                                            viewModel.getAppartements()
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
