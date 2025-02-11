
import bts.sio.azurimmo1.model.Appartement
import bts.sio.azurimmo1.model.Batiment

import bts.sio.azurimmo1.model.Intervention

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/batiments/")
    suspend fun getBatiments(): List<Batiment>

    @GET("api/appartements/")
    suspend fun getAppartements(): List<Appartement>
    abstract fun getInterventions(): List<Intervention>

    @GET("/api/appartements/batiment/{batimentId}")
    suspend fun getAppartementsByBatimentId(@Path("batimentId") batimentId: Int):
}


