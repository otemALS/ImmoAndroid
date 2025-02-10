package bts.sio.azurimmo1.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import RetrofitInstance
import bts.sio.azurimmo1.model.Intervention
import kotlinx.coroutines.launch

class InterventionViewModel : ViewModel() {
    private val _interventions = mutableStateOf<List<Intervention>>(emptyList())
    val interventions: State<List<Intervention>> = _interventions

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun getInterventionsByAppartementId(appartementId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.apiService.getInterventionsByAppartementId(appartementId)
                _interventions.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Erreur : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
