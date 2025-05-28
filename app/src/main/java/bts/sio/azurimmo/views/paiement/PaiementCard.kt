package bts.sio.azurimmo.views.paiement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import bts.sio.azurimmo.model.Paiement
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PaiementCard(paiement: Paiement, onClick: (Paiement) -> Unit) {
    val formattedDate = paiement.datePaiement?.let {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val parsedDate = inputFormat.parse(it)
            outputFormat.format(parsedDate!!)
        } catch (e: Exception) {
            "Date invalide"
        }
    } ?: "Aucune date"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(paiement) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Montant : ${paiement.montant ?: "Non renseigné"} €", fontWeight = FontWeight.Bold)
            Text("Date : $formattedDate")
            Text("Contrat ID : ${paiement.contrat?.id ?: "Aucun"}")
            Text("Locataire : ${paiement.contrat?.locataire?.prenom ?: "?"} ${paiement.contrat?.locataire?.nom ?: ""}")
        }
    }
}
