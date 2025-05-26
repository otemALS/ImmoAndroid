package bts.sio.azurimmo.views.appartement

import AppartementViewModel
import BatimentViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Appartement
import bts.sio.azurimmo.model.Batiment

@Composable
fun AppartementAddGlobal(
    viewModel: AppartementViewModel = viewModel(),
    batimentViewModel: BatimentViewModel = viewModel(),
    onAppartementAdded: () -> Unit
) {
    var numero by remember { mutableStateOf("") }
    var surface by remember { mutableStateOf("") }
    var nbrePieces by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedBatiment by remember { mutableStateOf<Batiment?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val batiments = batimentViewModel.batiments.value

    LaunchedEffect(Unit) {
        batimentViewModel.getBatiments()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        TextField(value = numero, onValueChange = { numero = it }, label = { Text("Numéro") }, modifier = Modifier.fillMaxWidth())
        TextField(value = surface, onValueChange = { surface = it }, label = { Text("Surface") }, modifier = Modifier.fillMaxWidth())
        TextField(value = nbrePieces, onValueChange = { nbrePieces = it }, label = { Text("Nombre de pièces") }, modifier = Modifier.fillMaxWidth())
        TextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = selectedBatiment?.adresse ?: "Sélectionner un bâtiment",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { dropdownExpanded = true }
                    .padding(12.dp)
                    .background(MaterialTheme.colorScheme.surface)
            )
            DropdownMenu(expanded = dropdownExpanded, onDismissRequest = { dropdownExpanded = false }) {
                batiments.forEach {
                    DropdownMenuItem(
                        text = { Text(it.adresse ?: "") },
                        onClick = {
                            selectedBatiment = it
                            dropdownExpanded = false
                        }
                    )

                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (selectedBatiment != null) {
                val newAppart = Appartement(
                    id = 0,
                    numero = numero,
                    surface = surface.toFloatOrNull() ?: 0f,
                    nbrePieces = nbrePieces.toIntOrNull() ?: 0,
                    description = description,
                    batiment = selectedBatiment!!
                )
                viewModel.addAppartement(newAppart)
                onAppartementAdded()
            }
        }) {
            Text("Enregistrer")
        }
    }
}
