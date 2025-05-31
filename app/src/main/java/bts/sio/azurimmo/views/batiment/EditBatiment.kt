package bts.sio.azurimmo.views.batiment

import BatimentViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Batiment


@Composable
fun EditBatiment(
    batiment: Batiment,
    viewModel: BatimentViewModel = viewModel(),
    onUpdated: () -> Unit
) {
    var adresse by remember { mutableStateOf(batiment.adresse ?: "") }
    var ville by remember { mutableStateOf(batiment.ville ?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Modifier un bâtiment", style = MaterialTheme.typography.titleLarge)

        TextField(
            value = adresse,
            onValueChange = { adresse = it },
            label = { Text("Adresse") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = ville,
            onValueChange = { ville = it },
            label = { Text("Ville") },
            modifier = Modifier.fillMaxWidth()
        )

        errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                if (adresse.isBlank() || ville.isBlank()) {
                    errorMessage = "Adresse et ville obligatoires"
                    return@Button
                }

                val updated = batiment.copy(adresse = adresse, ville = ville)
                viewModel.updateBatiment(updated) {
                    onUpdated()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Enregistrer")
        }
    }
}
