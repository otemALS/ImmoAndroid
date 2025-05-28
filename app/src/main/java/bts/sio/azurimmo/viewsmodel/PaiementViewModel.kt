package bts.sio.azurimmo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.RetrofitInstance
import bts.sio.azurimmo.model.Paiement
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class PaiementViewModel : ViewModel() {

    private val _paiements = mutableStateOf<List<Paiement>>(emptyList())
    val paiements: State<List<Paiement>> = _paiements

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun getPaiements() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _paiements.value = RetrofitInstance.api.getPaiements()
            } catch (e: Exception) {
                _errorMessage.value = "Erreur : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addPaiement(paiement: Paiement, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addPaiement(paiement)
                if (response.isSuccessful) {
                    getPaiements()
                    onSuccess(true)
                } else {
                    _errorMessage.value = "Erreur ajout : ${response.message()}"
                    onSuccess(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur réseau : ${e.message}"
                onSuccess(false)
            }
        }
    }

    fun updatePaiement(paiement: Paiement, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                paiement.id?.let {
                    val response = RetrofitInstance.api.updatePaiement(it, paiement)
                    if (response.isSuccessful) {
                        getPaiements()
                        onSuccess()
                    } else {
                        _errorMessage.value = "Erreur modification : ${response.message()}"
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur réseau : ${e.message}"
            }
        }
    }

    fun deletePaiement(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.deletePaiement(id)
                if (response.isSuccessful) {
                    getPaiements()
                    onSuccess()
                } else {
                    _errorMessage.value = "Erreur suppression : ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur réseau : ${e.message}"
            }
        }
    }

}

