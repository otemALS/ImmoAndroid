import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo1.viewsmodel.BatimentViewModel

// Fonction Composable pour afficher la liste des bâtiments
@Composable
fun BatimentList() {
// Récupérer le ViewModel dans le composable avec viewModel()
    val viewModel: BatimentViewModel = viewModel()
// Observer les données des bâtiments via le ViewModel
    val batiments = viewModel.batiments.value
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        items(batiments) { batiment ->
            BatimentCard(batiment = batiment) // Appel de la fonction BatimentCard
        }
    }
}




