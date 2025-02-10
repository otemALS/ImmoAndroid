import retrofit2.http.GET
import bts.sio.azurimmo1.model.Appartement

interface ApiService {
    @GET("appartements") // Remplace par le bon endpoint de ton API
    suspend fun getAppartements(): List<Appartement>
}
