import bts.sio.azurimmo1.model.Appartement

@Composable
fun AppartementList( viewModel: AppartementViewModel = viewModel()) {
    val appartements = viewModel.appartements.value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Erreur inconnue",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                LazyColumn {
                    items(batiments) { batiment ->
                        BatimentCard(batiment = batiment) // Appel de la fonction BatimentCard
                    }
                }
            }
        }
    }