package bts.sio.azurimmo.views.contrat

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Contrat
import ContratViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditContrat(
    contrat: Contrat,
    onUpdated: () -> Unit,
    viewModel: ContratViewModel = viewModel()
) {
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Champs modifiables
    var montantLoyer by remember { mutableStateOf(contrat.montantLoyer.toString()) }
    var montantCharges by remember { mutableStateOf(contrat.montantCharges.toString()) }
    var statut by remember { mutableStateOf(contrat.statut) }

    // Dates
    var dateEntree by remember { mutableStateOf(contrat.dateEntree ?: Date()) }
    var dateSortie by remember { mutableStateOf(contrat.dateSortie ?: Date()) }

    // Ouvreur de date
    fun openDatePicker(initial: Date, onDateSelected: (Date) -> Unit) {
        val calendar = Calendar.getInstance().apply { time = initial }
        DatePickerDialog(
            context,
            { _, year, month, day ->
                Calendar.getInstance().apply {
                    set(year, month, day)
                    onDateSelected(time)
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Modifier un contrat", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = montantLoyer, onValueChange = { montantLoyer = it }, label = { Text("Loyer") })
        OutlinedTextField(value = montantCharges, onValueChange = { montantCharges = it }, label = { Text("Charges") })
        OutlinedTextField(value = statut, onValueChange = { statut = it }, label = { Text("Statut") })

        Spacer(modifier = Modifier.height(16.dp))

        Text("Date d'entrée : ${dateFormat.format(dateEntree)}", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { openDatePicker(dateEntree) { dateEntree = it } }) {
            Text("Modifier date d'entrée")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Date de sortie : ${dateFormat.format(dateSortie)}", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { openDatePicker(dateSortie) { dateSortie = it } }) {
            Text("Modifier date de sortie")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val updated = contrat.copy(
                montantLoyer = montantLoyer.toDoubleOrNull() ?: 0.0,
                montantCharges = montantCharges.toDoubleOrNull() ?: 0.0,
                statut = statut,
                dateEntree = dateEntree,
                dateSortie = dateSortie
            )
            viewModel.updateContrat(updated) {
                onUpdated()
            }
        }) {
            Text("Enregistrer")
        }
    }
}
