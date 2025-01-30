import bts.sio.azurimmo1.model.Batiment

import retrofit2.http.GET


interface ApiService {
    @GET("api/batiments/")
    suspend fun getBatiments(): List<Batiment>
}