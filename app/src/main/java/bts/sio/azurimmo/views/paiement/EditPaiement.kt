package bts.sio.azurimmo.views.paiement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import bts.sio.azurimmo.model.Paiement
import bts.sio.azurimmo.viewmodel.PaiementViewModel

@Composable
fun EditPaiement(
    paiement: Paiement,
    viewModel: PaiementViewModel,
    onUpdated: () -> Unit
) {
    var montant by remember { mutableStateOf(paiement.montant?.toString() ?: "") }
    var datePaiement by remember { mutableStateOf(paiement.datePaiement ?: "") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Modifier un paiement", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = montant,
            onValueChange = { montant = it },
            label = { Text("Montant (€)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = datePaiement,
            onValueChange = { datePaiement = it },
            label = { Text("Date de paiement (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                val montantFloat = montant.toFloatOrNull()
                if (montantFloat == null) {
                    errorMessage = "Montant invalide"
                    return@Button
                }

                val updated = paiement.copy(
                    montant = montantFloat,
                    datePaiement = datePaiement
                )

                viewModel.updatePaiement(updated) {
                    onUpdated()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enregistrer")
        }
    }
}
