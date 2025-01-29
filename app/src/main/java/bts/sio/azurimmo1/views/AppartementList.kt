import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo1.viewsmodel.AppartementViewModel

// Fonction Composable pour afficher la liste des appartements
@Composable
fun AppartementList() {
// Récupérer le ViewModel dans le composable avec viewModel()
    val viewModel: AppartementViewModel = viewModel()
// Observer les données des appartements via le ViewModel
    val appartement = viewModel.appartements.value
    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        items(appartement) { appartement ->
            AppartementCard(appartement = appartement) // Appel de la fonction BatimentCard
        }
    }
}




