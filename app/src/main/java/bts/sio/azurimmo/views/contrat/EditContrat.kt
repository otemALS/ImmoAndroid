package bts.sio.azurimmo.views.contrat


import AppartementViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Contrat
import bts.sio.azurimmo.viewmodel.ContratViewModel
import bts.sio.azurimmo.viewsmodel.LocataireViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContrat(contrat: Contrat, onUpdated: () -> Unit) {
    val contratViewModel: ContratViewModel = viewModel()
    val locataireViewModel: LocataireViewModel = viewModel()
    val appartementViewModel: AppartementViewModel = viewModel()

    LaunchedEffect(Unit) {
        locataireViewModel.getLocataires()
        appartementViewModel.getAppartements()
    }

    val locataires = locataireViewModel.locataires.value
    val appartements = appartementViewModel.appartements.value

    var montantLoyer by remember { mutableStateOf(contrat.montantLoyer.toString()) }
    var montantCharges by remember { mutableStateOf(contrat.montantCharges.toString()) }
    var statut by remember { mutableStateOf(contrat.statut) }
    var selectedLocataire by remember { mutableStateOf(contrat.locataire) }
    var selectedAppartement by remember { mutableStateOf(contrat.appartement) }

    var dateEntreeString by remember { mutableStateOf(contrat.dateEntree) }
    var dateSortieString by remember { mutableStateOf(contrat.dateSortie) }

    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Modifier le contrat", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Date d'entrée : $dateEntreeString")
        Button(onClick = {
            val cal = Calendar.getInstance()
            android.app.DatePickerDialog(
                context,
                { _, y, m, d ->
                    cal.set(y, m, d)
                    dateEntreeString =
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }) {
            Text("Modifier la date d'entrée")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Date de sortie : $dateSortieString")
        Button(onClick = {
            val cal = Calendar.getInstance()
            android.app.DatePickerDialog(
                context,
                { _, y, m, d ->
                    cal.set(y, m, d)
                    dateSortieString =
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }) {
            Text("Modifier la date de sortie")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = montantLoyer, onValueChange = { montantLoyer = it }, label = { Text("Loyer") })
        OutlinedTextField(value = montantCharges, onValueChange = { montantCharges = it }, label = { Text("Charges") })
        OutlinedTextField(value = statut, onValueChange = { statut = it }, label = { Text("Statut") })

        Spacer(modifier = Modifier.height(12.dp))

        Text("Locataire actuel : ${selectedLocataire?.nom ?: "Aucun"}")
        var locataireExpanded by remember { mutableStateOf(false) }
        Box {
            OutlinedButton(onClick = { locataireExpanded = true }) {
                Text("Changer le locataire")
            }
            DropdownMenu(expanded = locataireExpanded, onDismissRequest = { locataireExpanded = false }) {
                locataires.forEach { loc ->
                    DropdownMenuItem(
                        text = { Text("${loc.nom} ${loc.prenom}") },
                        onClick = {
                            selectedLocataire = loc
                            locataireExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Appartement actuel : ${selectedAppartement?.numero ?: "Aucun"}")
        var appartementExpanded by remember { mutableStateOf(false) }
        Box {
            OutlinedButton(onClick = { appartementExpanded = true }) {
                Text("Changer l'appartement")
            }
            DropdownMenu(expanded = appartementExpanded, onDismissRequest = { appartementExpanded = false }) {
                appartements.forEach { app ->
                    DropdownMenuItem(
                        text = { Text("N°${app.numero} - ${app.surface}m²") },
                        onClick = {
                            selectedAppartement = app
                            appartementExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val updated = contrat.copy(
                    dateEntree = dateEntreeString,
                    dateSortie = dateSortieString,
                    montantLoyer = montantLoyer.toFloatOrNull() ?: 0f,
                    montantCharges = montantCharges.toFloatOrNull() ?: 0f,
                    statut = statut,
                    locataire = selectedLocataire!!,
                    appartement = selectedAppartement!!
                )
                contratViewModel.updateContrat(updated)
                onUpdated()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Filled.Save, contentDescription = "Enregistrer")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Enregistrer")
        }
    }
}
