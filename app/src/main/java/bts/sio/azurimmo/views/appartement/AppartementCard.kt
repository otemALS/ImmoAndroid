package bts.sio.azurimmo.views.appartement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import bts.sio.azurimmo.model.Appartement

@Composable
fun AppartementCard(appartement: Appartement, onClick: (Appartement) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(appartement) },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Numéro : ${appartement.numero}", fontWeight = FontWeight.Bold)
            Text("Surface : ${appartement.surface} m²")
            Text("Pièces : ${appartement.nbrePieces}")
            Text("Description : ${appartement.description}")
            Text("Bâtiment : ${appartement.batiment.adresse ?: "Non défini"}")
        }
    }
}
