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
import bts.sio.azurimmo.model.Locataire
import bts.sio.azurimmo.viewsmodel.LocataireViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditLocataire(
    locataire: Locataire,
    onUpdated: () -> Unit,
    viewModel: LocataireViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current

    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var dateN by remember { mutableStateOf(inputFormat.parse(locataire.dateN) ?: Date()) }

    var nom by remember { mutableStateOf(locataire.nom ?: "") }
    var prenom by remember { mutableStateOf(locataire.prenom ?: "") }
    var lieuN by remember { mutableStateOf(locataire.lieuN ?: "") }

    val displayFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(text = "Modifier le locataire", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = nom, onValueChange = { nom = it }, label = { Text("Nom") })
        OutlinedTextField(value = prenom, onValueChange = { prenom = it }, label = { Text("Prénom") })
        OutlinedTextField(value = lieuN, onValueChange = { lieuN = it }, label = { Text("Lieu de naissance") })

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Date de naissance : ${displayFormat.format(dateN)}")

        Button(onClick = {
            val cal = Calendar.getInstance().apply { time = dateN }
            DatePickerDialog(context, { _, year, month, day ->
                Calendar.getInstance().apply {
                    set(year, month, day)
                    dateN = time
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }) {
            Text("Modifier la date")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val apiFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val updated = locataire.copy(
                    nom = nom,
                    prenom = prenom,
                    lieuN = lieuN,
                    dateN = apiFormat.format(dateN)
                )
                viewModel.updateLocataire(locataire.id!!, updated) { success ->
                    if (success) {
                        println(">>> Modification réussie")
                        onUpdated() // ⚠️ C’est ça qui doit déclencher le retour ET le refresh
                    } else {
                        println(">>> Échec modification")
                    }
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
