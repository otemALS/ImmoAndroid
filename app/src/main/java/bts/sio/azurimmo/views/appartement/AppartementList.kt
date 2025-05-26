package bts.sio.azurimmo.views.appartement

import AppartementViewModel
import BatimentViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bts.sio.azurimmo.model.Appartement

@Composable
fun AppartementList(
    navController: NavHostController,
    viewModel: AppartementViewModel = viewModel(),
    batimentId: Int,
    onAddAppartementClick: () -> Unit
) {
    val viewModelBat: BatimentViewModel = viewModel()
    val appartements = viewModel.appartements.value.filter { it.batiment.id == batimentId }
    val batiment = viewModelBat.batiment.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    var selectedAppartement by remember { mutableStateOf<Appartement?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(batimentId) {
        viewModel.getAppartementsByBatimentId(batimentId)
        viewModelBat.getBatiment(batimentId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    if (batiment != null) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Informations sur le bâtiment",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Adresse : ${batiment.adresse}")
                                Text("Ville : ${batiment.ville}")
                            }
                        }

                        if (appartements.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Liste des appartements",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            items(appartements) { appartement ->
                                AppartementCard(appartement = appartement) {
                                    selectedAppartement = it
                                    showDialog = true
                                }
                            }
                        } else {
                            item {
                                Text(
                                    text = "Pas d'appartement pour ce bâtiment",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }

                // ✅ FAB
                FloatingActionButton(
                    onClick = onAddAppartementClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Ajouter un appartement")
                }

                // ✅ Dialog Modifier / Supprimer
                if (showDialog && selectedAppartement != null) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Actions pour l'appartement ${selectedAppartement?.numero}") },
                        text = { Text("Que souhaitez-vous faire ?") },
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
                                        viewModel.getAppartementsByBatimentId(batimentId)
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
