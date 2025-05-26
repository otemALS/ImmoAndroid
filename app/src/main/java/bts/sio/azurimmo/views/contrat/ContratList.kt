import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import bts.sio.azurimmo.model.Contrat


@Composable
fun ContratList(navController: NavHostController, viewModel: ContratViewModel = viewModel()) {
    val contrats = viewModel.contrats.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    var selectedContrat by remember { mutableStateOf<Contrat?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            errorMessage != null -> Text(errorMessage, Modifier.align(Alignment.Center))
            else -> {
                LazyColumn {
                    items(contrats) { contrat ->
                        ContratCard(contrat = contrat) {
                            selectedContrat = it
                            showDialog = true
                        }
                    }
                }

                if (showDialog && selectedContrat != null) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Actions sur le contrat") },
                        text = { Text("Modifier ou supprimer ce contrat ?") },
                        confirmButton = {
                            TextButton(onClick = {
                                navController.navigate("edit_contrat/${selectedContrat!!.id}")
                                showDialog = false
                            }) {
                                Text("Modifier")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                viewModel.deleteContrat(selectedContrat!!.id) {}
                                showDialog = false
                            }) {
                                Text("Supprimer")
                            }
                        }
                    )
                }
            }
        }
    }
}
