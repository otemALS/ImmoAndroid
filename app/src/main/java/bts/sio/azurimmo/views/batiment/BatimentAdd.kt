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
fun BatimentAdd(onBatimentAdd: () -> Unit) {
    val viewModel: BatimentViewModel = viewModel()
    var adresse by remember { mutableStateOf("") }
    var ville by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = adresse,
            onValueChange = { adresse = it },
            label = { Text("Adresse") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = ville,
            onValueChange = { ville = it },
            label = { Text("Ville") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (adresse.isNotBlank() && ville.isNotBlank()) {
                    val batiment = Batiment(id = null, adresse = adresse, ville = ville)
                    viewModel.addBatiment(batiment)
                    onBatimentAdd()
                }
            },
            modifier = Modifier.align(Alignment.End),
            enabled = adresse.isNotBlank() && ville.isNotBlank()
        ) {
            Text("Ajouter le bâtiment")
        }
    }
}
