import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import bts.sio.azurimmo.api.RetrofitInstance
import bts.sio.azurimmo.model.Garant
import kotlinx.coroutines.launch

class GarantViewModel : ViewModel() {

    // Liste mutable des interventions
    private val _garants = mutableStateOf<List<Garant>>(emptyList())
    val garants: State<List<Garant>> = _garants

    // État de chargement
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // Message d'erreur
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        // Charger les interventions au démarrage
        getGarants()
    }

    private fun getGarants() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Appel de l'API
                val response = RetrofitInstance.api.getGarants()
                _garants.value = response
            } catch (e: Exception) {
                _errorMessage.value = "Erreur : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
