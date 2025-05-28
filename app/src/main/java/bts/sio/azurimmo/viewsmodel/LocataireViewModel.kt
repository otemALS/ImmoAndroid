package bts.sio.azurimmo.viewsmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.RetrofitInstance
import bts.sio.azurimmo.model.Locataire
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class LocataireViewModel : ViewModel() {

    private val _locataires = mutableStateOf<List<Locataire>>(emptyList())
    val locataires: State<List<Locataire>> = _locataires

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        getLocataires()
    }

    fun getLocataires() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getLocataires()
                if (response.isSuccessful) {
                    _locataires.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Erreur serveur : ${response.code()}"
                }

            } catch (e: Exception) {
                _errorMessage.value = "Erreur : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addLocataire(locataire: Locataire, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addLocataire(locataire)
                if (response.isSuccessful) {
                    getLocataires()
                    onSuccess(true)
                } else {
                    println(">>> Échec ajout : ${response.code()} - ${response.errorBody()?.string()}")
                    onSuccess(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur ajout : ${e.message}"
                onSuccess(false)
            }
        }
    }

    fun updateLocataire(id: Int, locataire: Locataire, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.updateLocataire(id, locataire)
                if (response.isSuccessful) {
                    getLocataires()
                    onSuccess(true)
                } else {
                    _errorMessage.value = "Erreur mise à jour : ${response.message()}"
                    onSuccess(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur : ${e.message}"
                onSuccess(false)
            }
        }
    }

    fun deleteLocataire(id: Int, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deleteLocataire(id)
                if (response.isSuccessful) {
                    getLocataires()
                    onSuccess(true)
                } else {
                    _errorMessage.value = "Erreur suppression : ${response.message()}"
                    onSuccess(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur : ${e.message}"
                onSuccess(false)
            }
        }
    }

}
