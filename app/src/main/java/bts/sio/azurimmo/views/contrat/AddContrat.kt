package bts.sio.azurimmo.views.contrat

import AppartementViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Appartement
import bts.sio.azurimmo.model.Contrat
import bts.sio.azurimmo.model.Locataire
import bts.sio.azurimmo.viewmodel.ContratViewModel
import bts.sio.azurimmo.viewsmodel.LocataireViewModel

@Composable
fun AddContrat(onContractAdded: () -> Unit) {
    val contratViewModel: ContratViewModel = viewModel()
    val locataireViewModel: LocataireViewModel = viewModel()
    val appartementViewModel: AppartementViewModel = viewModel()

    LaunchedEffect(Unit) {
        locataireViewModel.getLocataires()
        appartementViewModel.getAppartements()
    }

    val locataires = locataireViewModel.locataires.value
    val appartements = appartementViewModel.appartements.value
    val error = contratViewModel.errorMessage.value

    var selectedLocataire by remember { mutableStateOf<Locataire?>(null) }
    var selectedAppartement by remember { mutableStateOf<Appartement?>(null) }

    var dateEntree by remember { mutableStateOf("") }
    var dateSortie by remember { mutableStateOf("") }
    var montantLoyer by remember { mutableStateOf("") }
    var montantCharges by remember { mutableStateOf("") }
    var statut by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Créer un contrat", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = dateEntree,
            onValueChange = { dateEntree = it },
            label = { Text("Date d'entrée (yyyy-MM-dd)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dateSortie,
            onValueChange = { dateSortie = it },
            label = { Text("Date de sortie (yyyy-MM-dd)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = montantLoyer,
            onValueChange = { montantLoyer = it },
            label = { Text("Montant du loyer") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = montantCharges,
            onValueChange = { montantCharges = it },
            label = { Text("Montant des charges") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = statut,
            onValueChange = { statut = it },
            label = { Text("Statut") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Sélectionner un locataire :")
        var locataireDropdownExpanded by remember { mutableStateOf(false) }

        Box {
            OutlinedButton(onClick = { locataireDropdownExpanded = true }) {
                Text(selectedLocataire?.nom ?: "Choisir un locataire")
            }

            DropdownMenu(
                expanded = locataireDropdownExpanded,
                onDismissRequest = { locataireDropdownExpanded = false }
            ) {
                locataires.forEach { loc ->
                    DropdownMenuItem(
                        text = { Text("${loc.nom} ${loc.prenom}") },
                        onClick = {
                            selectedLocataire = loc
                            locataireDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Sélectionner un appartement :")
        var appartementDropdownExpanded by remember { mutableStateOf(false) }

        Box {
            OutlinedButton(onClick = { appartementDropdownExpanded = true }) {
                Text(selectedAppartement?.numero?.toString() ?: "Choisir un appartement")
            }

            DropdownMenu(
                expanded = appartementDropdownExpanded,
                onDismissRequest = { appartementDropdownExpanded = false }
            ) {
                appartements.forEach { app ->
                    DropdownMenuItem(
                        text = { Text("N°${app.numero} - ${app.surface}m²") },
                        onClick = {
                            selectedAppartement = app
                            appartementDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (selectedLocataire != null && selectedAppartement != null) {
                    val contrat = Contrat(
                        id = 0,
                        dateEntree = dateEntree,
                        dateSortie = dateSortie,
                        montantLoyer = montantLoyer.toFloatOrNull() ?: 0f,
                        montantCharges = montantCharges.toFloatOrNull() ?: 0f,
                        statut = statut,
                        locataire = selectedLocataire!!,
                        appartement = selectedAppartement!!
                    )

                    contratViewModel.addContrat(contrat) {
                        onContractAdded()
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Filled.Save, contentDescription = "Enregistrer")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Enregistrer")
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Erreur : $error", color = MaterialTheme.colorScheme.error)
        }
    }
}
