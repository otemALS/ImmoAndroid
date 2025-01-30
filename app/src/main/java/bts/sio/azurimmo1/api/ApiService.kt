import bts.sio.azurimmo1.model.Appartement
import bts.sio.azurimmo1.model.Batiment

import retrofit2.http.GET


interface ApiService {
    @GET("api/batiments/")
    suspend fun getBatiments(): List<Batiment>


        @GET("api/appartements/")
        suspend fun getAppartements(): List<Appartement>
    }
