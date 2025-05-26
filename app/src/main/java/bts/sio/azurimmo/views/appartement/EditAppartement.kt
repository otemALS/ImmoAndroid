package bts.sio.azurimmo.views.appartement

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Appartement
import AppartementViewModel

@Composable
fun EditAppartement(
    appartement: Appartement,
    onUpdated: () -> Unit
) {
    // ✅ Corrigé ici : récupération du ViewModel
    val viewModel: AppartementViewModel = viewModel()

    var numero by remember { mutableStateOf(appartement.numero) }
    var description by remember { mutableStateOf(appartement.description) }
    var surface by remember { mutableStateOf(appartement.surface.toString()) }
    var nbrePieces by remember { mutableStateOf(appartement.nbrePieces.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Modifier l'appartement", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = numero,
            onValueChange = { numero = it },
            label = { Text("Numéro") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = surface,
            onValueChange = { surface = it },
            label = { Text("Surface") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = nbrePieces,
            onValueChange = { nbrePieces = it },
            label = { Text("Nombre de pièces") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val updated = appartement.copy(
                    numero = numero,
                    surface = surface.toFloatOrNull() ?: 0f,
                    nbrePieces = nbrePieces.toIntOrNull() ?: 0,
                    description = description
                )
                viewModel.updateAppartement(updated) {
                    onUpdated()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Enregistrer")
        }
    }
}
