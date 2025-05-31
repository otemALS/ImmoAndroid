package bts.sio.azurimmo.views.batiment

import BatimentViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bts.sio.azurimmo.model.Batiment

@Composable
fun BatimentCard(
    batiment: Batiment,
    onClick: (Int) -> Unit,
    onMoreClick: (Batiment) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { batiment.id?.let { onClick(it) } },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = batiment.adresse ?: "Adresse non disponible", style = MaterialTheme.typography.bodyLarge)
                Text(text = batiment.ville ?: "Ville non disponible", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onMoreClick(batiment) }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Options")
            }
        }
    }
}


