import bts.sio.azurimmo1.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class RetrofitInstance {
    companion object {
        private const val BASE_URL = "http://10.0.2.2:9008/"

        val api: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}



