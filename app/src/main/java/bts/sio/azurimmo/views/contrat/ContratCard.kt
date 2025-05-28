package bts.sio.azurimmo.views.contrat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bts.sio.azurimmo.model.Contrat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ContratCard(contrat: Contrat, onClick: () -> Unit = {}) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val dateEntreeFormatted = try {
        outputFormat.format(inputFormat.parse(contrat.dateEntree)!!)
    } catch (e: Exception) {
        contrat.dateEntree
    }

    val dateSortieFormatted = try {
        outputFormat.format(inputFormat.parse(contrat.dateSortie)!!)
    } catch (e: Exception) {
        contrat.dateSortie
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Contrat ${contrat.id}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Locataire : ${contrat.locataire.nom} ${contrat.locataire.prenom}")
            Text("Appartement n°${contrat.appartement.numero} - ${contrat.appartement.surface}m²")
            Text("Entrée : $dateEntreeFormatted")
            Text("Sortie : $dateSortieFormatted")
            Text("Loyer : ${contrat.montantLoyer} €")
            Text("Charges : ${contrat.montantCharges} €")
            Text("Statut : ${contrat.statut}")
        }
    }
}
