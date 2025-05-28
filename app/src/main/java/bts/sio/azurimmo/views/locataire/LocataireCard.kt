package bts.sio.azurimmo.views.locataire

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bts.sio.azurimmo.model.Locataire
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LocataireCard(locataire: Locataire, onClick: (Locataire) -> Unit) {
    val formattedDateDateN = try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val parsedDate = inputFormat.parse(locataire.dateN)
        outputFormat.format(parsedDate!!)
    } catch (e: Exception) {
        "Date invalide : ${locataire.dateN}"
    }

    Column(
        modifier = Modifier
            .clickable { onClick(locataire) }
            .padding(16.dp)
    ) {
        Text("Nom : ${locataire.nom}", style = MaterialTheme.typography.bodyLarge)
        Text("Prénom : ${locataire.prenom}", style = MaterialTheme.typography.bodyLarge)
        Text("Date de naissance : $formattedDateDateN", style = MaterialTheme.typography.bodyLarge)
        Text("Lieu de naissance : ${locataire.lieuN}", style = MaterialTheme.typography.bodyLarge)
    }
}
