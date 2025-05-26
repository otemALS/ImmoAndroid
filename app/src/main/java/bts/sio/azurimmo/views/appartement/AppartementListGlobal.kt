package bts.sio.azurimmo.views.appartement

import AppartementViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppartementListGlobal(
    viewModel: AppartementViewModel = viewModel(),
    onAddClick: () -> Unit
) {
    val appartements = viewModel.appartements.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value

    LaunchedEffect(Unit) {
        viewModel.getAppartements() // sans filtre
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            errorMessage != null -> Text(errorMessage, Modifier.align(Alignment.Center))
            else -> {
                Column {
                    FloatingActionButton(
                        onClick = onAddClick,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Ajouter un appartement")
                    }

                    LazyColumn {
                        items(appartements) { appartement ->
                            AppartementCard(
                                appartement,
                                onClick = TODO()
                            )
                        }
                    }
                }
            }
        }
    }
}
