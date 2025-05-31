package bts.sio.azurimmo.views.contrat

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
import bts.sio.azurimmo.model.Contrat
import bts.sio.azurimmo.viewmodel.ContratViewModel



@Composable
fun ContratList(
    navController: NavHostController,
    viewModel: ContratViewModel = viewModel()
) {
    val contrats by viewModel.contrats
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    var showDialog by remember { mutableStateOf(false) }
    var selectedContrat by remember { mutableStateOf<Contrat?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            errorMessage != null -> Text(errorMessage ?: "", Modifier.align(Alignment.Center))
            else -> {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 72.dp)
                ) {
                    items(contrats) { contrat ->
                        ContratCard(contrat = contrat, onClick = {
                            selectedContrat = contrat
                            showDialog = true
                        })
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("add_contrat") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Ajouter un contrat")
        }
    }

    if (showDialog && selectedContrat != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Contrat ${selectedContrat!!.id}") },
            text = {
                Text("Statut : ${selectedContrat!!.statut}\n" +
                        "Loyer : ${selectedContrat!!.montantLoyer} €\n" +
                        "Charges : ${selectedContrat!!.montantCharges} €")
            },
            confirmButton = {
                TextButton(onClick = {
                    navController.navigate("edit_contrat/${selectedContrat!!.id}")
                    showDialog = false
                }) {
                    Text("Modifier")
                }
            },
            dismissButton = {
                Row {
                    TextButton(onClick = {
                        selectedContrat?.id?.let {
                            viewModel.deleteContrat(it) {
                                viewModel.getContrats()
                                showDialog = false
                            }
                        }
                    }) {
                        Text("Supprimer")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { showDialog = false }) {
                        Text("Fermer")
                    }
                }
            }
        )
    }
}
