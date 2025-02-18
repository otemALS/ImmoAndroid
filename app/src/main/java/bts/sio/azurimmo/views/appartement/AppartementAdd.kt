package bts.sio.azurimmo.views.appartement

import AppartementViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Appartement


@Composable
fun AppartementAdd(onAppartementAdd: () -> Unit) {
    val viewModel: AppartementViewModel = viewModel()

    // Champs pour l'ajout de l'appartement
    var numero by remember { mutableStateOf("") }
    var surface by remember { mutableStateOf("") }
    var nbrePieces by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = numero,
            onValueChange = { numero = it },
            label = { Text("Numéro de l'appartement") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = surface,
            onValueChange = { surface = it },
            label = { Text("Surface (en m²)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = nbrePieces,
            onValueChange = { nbrePieces = it },
            label = { Text("Nombre de pièces") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (numero.isNotBlank() && surface.isNotBlank() && nbrePieces.isNotBlank()) {
                    val appartement = Appartement(
                        id = null,  // Utilisation de null pour que l'ID soit auto-généré
                        numero = numero,
                        surface = surface.toDouble(),
                        nbrePieces = nbrePieces.toInt(),
                        description = description
                    )
                    viewModel.addAppartement(appartement)
                    onAppartementAdd() // On notifie le parent que l’ajout est fait
                }
            },
            modifier = Modifier.align(Alignment.End),
            enabled = numero.isNotBlank() && surface.isNotBlank() && nbrePieces.isNotBlank()
        ) {
            Text("Ajouter l'appartement")
        }
    }
}
