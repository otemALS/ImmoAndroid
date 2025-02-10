import bts.sio.azurimmo1.model.Appartement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:9008/") // Remplace par l'URL correcte de ton API
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiEndpoints::class.java)

    suspend fun getAppartements() = api.getAppartements()

    // Déclaration des endpoints dans une interface interne
    private interface ApiEndpoints {
        @GET("appartements") // Remplace par le bon endpoint de ton API
        suspend fun getAppartements(): List<Appartement>
    }
}
