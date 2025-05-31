package bts.sio.azurimmo.views.batiment

import BatimentViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Batiment

// Fonction Composable pour afficher la liste des bâtiments avec un bouton d'ajout
@Composable
fun BatimentList(
    viewModel: BatimentViewModel = viewModel(),
    onBatimentClick: (Int) -> Unit,
    onAddBatimentClick: () -> Unit
) {
    val batiments = viewModel.batiments.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    var showDialog by remember { mutableStateOf(false) }
    var selectedBatiment by remember { mutableStateOf<Batiment?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getBatiments()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Erreur inconnue",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = onAddBatimentClick,
                        modifier = Modifier
                            .padding(16.dp)
                            .widthIn(min = 150.dp, max = 300.dp)
                    ) {
                        Text("Ajouter un bâtiment")
                    }

                    LazyColumn {
                        items(batiments) { batiment ->
                            BatimentCard(
                                batiment = batiment,
                                onClick = { batiment.id?.let { onBatimentClick(it) } },
                                onMoreClick = {
                                    selectedBatiment = it
                                    showDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog && selectedBatiment != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Bâtiment ${selectedBatiment!!.id ?: ""}") },
            text = {
                Text(
                    "Adresse : ${selectedBatiment!!.adresse ?: "-"}\n" +
                            "Ville : ${selectedBatiment!!.ville ?: "-"}"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    selectedBatiment?.id?.let {
                        onBatimentClick(it)
                        showDialog = false
                    }
                }) {
                    Text("Modifier")
                }
            },
            dismissButton = {
                Row {
                    TextButton(onClick = {
                        selectedBatiment?.id?.let {
                            viewModel.deleteBatiment(it) {
                                viewModel.getBatiments()
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

