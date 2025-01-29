package bts.sio.azurimmo1.viewsmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import bts.sio.azurimmo1.model.Appartement
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
// ViewModel pour gérer les données des appartements
class AppartementViewModel : ViewModel() {
    // Liste mutable des appartements
    private val _appartements = mutableStateOf(emptyList<Appartement>())
    val appartements: State<List<Appartement>> = _appartements
    init {
// Simuler un chargement de données initiales
        getAppartements()
    }

    private fun getAppartements() {
        TODO("Not yet implemented")
    }


    // Fonction pour simuler le chargement de bâtiments
    private fun loadAppartements() {
        viewModelScope.launch {
            _appartements.value = listOf(
                Appartement(
                    id = 1,
                    description = "Appartement cosy avec balcon",
                    numero = "A101",
                    nbPiece = "3",
                    surface = "75"
                ),
                Appartement(
                    id = 2,
                    description = "Studio lumineux en centre-ville",
                    numero = "B202",
                    nbPiece = "1",
                    surface = "30"
                ),
                Appartement(
                    id = 3,
                    description = "Grand duplex avec terrasse",
                    numero = "C303",
                    nbPiece = "5",
                    surface = "120"
                )

            )
        }
    }
}