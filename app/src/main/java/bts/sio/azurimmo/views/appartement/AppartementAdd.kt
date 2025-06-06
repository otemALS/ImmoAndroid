package bts.sio.azurimmo.views.appartement

import AppartementViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Appartement
import bts.sio.azurimmo.model.Batiment

@Composable
fun AppartementAdd(onAddAppartement: (Appartement) -> Unit, batimentId: Int) {
    val viewModel: AppartementViewModel = viewModel()

    var description by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var nbrePieces by remember { mutableStateOf("") }
    var surface by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Ajout d'un appartement", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = numero,
            onValueChange = { numero = it },
            label = { Text("Numéro") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = surface,
            onValueChange = { surface = it },
            label = { Text("Surface (m²)") },
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

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val batiment = Batiment(id = batimentId, adresse = "", ville = "")
                val appartement = Appartement(
                    id = 0,
                    numero = numero,
                    description = description,
                    surface = surface.toFloatOrNull() ?: 0f,
                    batiment = batiment,
                    nbrePieces = nbrePieces.toIntOrNull() ?: 0
                )
                viewModel.addAppartement(appartement)
                viewModel.getAppartementsByBatimentId(batimentId)
                onAddAppartement(appartement)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Ajouter l'appartement")
        }
    }
}
