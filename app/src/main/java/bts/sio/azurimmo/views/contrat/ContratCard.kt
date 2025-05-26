import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import bts.sio.azurimmo.model.Contrat

@Composable
fun ContratCard(contrat: Contrat, onClick: (Contrat) -> Unit) {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", java.util.Locale.getDefault())

    val formattedDateEntree = contrat.dateEntree?.let { dateFormat.format(it) } ?: "Non définie"
    val formattedDateSortie = contrat.dateSortie?.let { dateFormat.format(it) } ?: "Non définie"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(contrat) },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Date d'entrée : $formattedDateEntree", style = MaterialTheme.typography.bodyLarge)
            Text("Date de sortie : $formattedDateSortie", style = MaterialTheme.typography.bodyMedium)
            Text("Loyer : ${contrat.montantLoyer} €", style = MaterialTheme.typography.bodyMedium)
            Text("Charges : ${contrat.montantCharges} €", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
