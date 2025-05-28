package bts.sio.azurimmo.views.paiement


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.model.Paiement
import bts.sio.azurimmo.viewmodel.PaiementViewModel

@Composable
fun AddPaiement(onPaiementAdded: () -> Unit) {
    val viewModel: PaiementViewModel = viewModel()

    var montant by remember { mutableStateOf("") }
    var datePaiement by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Ajouter un paiement", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = montant,
            onValueChange = { montant = it },
            label = { Text("Montant") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = datePaiement,
            onValueChange = { datePaiement = it },
            label = { Text("Date de paiement (yyyy-MM-dd)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val paiement = Paiement(
                    montant = montant.toFloatOrNull(),
                    datePaiement = datePaiement
                )
                viewModel.addPaiement(paiement) { success ->
                    if (success) onPaiementAdded()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Ajouter")
        }
    }
}
