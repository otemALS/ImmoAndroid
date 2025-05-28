package bts.sio.azurimmo.views.locataire

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Locataire
import bts.sio.azurimmo.viewsmodel.LocataireViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddLocataire(onLocataireAdded: () -> Unit) {
    val context = LocalContext.current
    val viewModel: LocataireViewModel = viewModel()

    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var lieuN by remember { mutableStateOf("") }
    var dateN by remember { mutableStateOf(Date()) }

    val dateFormatDisplay = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val dateFormatAPI = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Ajouter un locataire", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = nom, onValueChange = { nom = it }, label = { Text("Nom") })
        OutlinedTextField(value = prenom, onValueChange = { prenom = it }, label = { Text("Prénom") })
        OutlinedTextField(value = lieuN, onValueChange = { lieuN = it }, label = { Text("Lieu de naissance") })

        Spacer(modifier = Modifier.height(16.dp))

        Text("Date de naissance : ${dateFormatDisplay.format(dateN)}")
        Button(onClick = {
            val cal = Calendar.getInstance().apply { time = dateN }
            DatePickerDialog(context, { _, year, month, day ->
                Calendar.getInstance().apply {
                    set(year, month, day)
                    dateN = time
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }) {
            Text("Sélectionner une date")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val locataire = Locataire(
                    id = null,
                    nom = nom,
                    prenom = prenom,
                    lieuN = lieuN,
                    dateN = dateFormatAPI.format(dateN)
                )
                viewModel.addLocataire(locataire) { success ->
                    if (success) onLocataireAdded()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Filled.Save, contentDescription = "Enregistrer")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Enregistrer")
        }
    }
}
