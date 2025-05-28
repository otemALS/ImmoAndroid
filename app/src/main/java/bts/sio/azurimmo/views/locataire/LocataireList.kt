package bts.sio.azurimmo.views.locataire


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
import bts.sio.azurimmo.model.Locataire
import bts.sio.azurimmo.viewsmodel.LocataireViewModel


@Composable
fun LocataireList(navController: NavHostController) {
    val viewModel: LocataireViewModel = viewModel()

    // Recharge automatique de la liste à chaque affichage
    LaunchedEffect(Unit) {
        viewModel.getLocataires()
    }

    val locataires = viewModel.locataires.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    var selectedLocataire by remember { mutableStateOf<Locataire?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            errorMessage != null -> Text(errorMessage, Modifier.align(Alignment.Center))
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(locataires) { locataire ->
                        LocataireCard(locataire = locataire) {
                            selectedLocataire = it
                            showDialog = true
                        }
                    }
                }

                FloatingActionButton(
                    onClick = { navController.navigate("add_locataire") },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Ajouter un locataire")
                }

                if (showDialog && selectedLocataire != null) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Action sur le locataire") },
                        text = { Text("Modifier ou supprimer ce locataire ?") },
                        confirmButton = {
                            TextButton(onClick = {
                                selectedLocataire?.id?.let {
                                    navController.navigate("edit_locataire/$it")
                                }
                                showDialog = false
                            }) {
                                Text("Modifier")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                selectedLocataire?.id?.let {
                                    viewModel.deleteLocataire(it) { success ->
                                        if (success) {
                                            println(">>> Suppression réussie")
                                        } else {
                                            println(">>> Échec suppression")
                                        }
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
