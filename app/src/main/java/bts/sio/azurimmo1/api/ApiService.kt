package bts.sio.azurimmo1.api

import bts.sio.azurimmo1.model.Intervention
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/interventions/appartement/{appartementId}")
    suspend fun getInterventionsByAppartementId(@Path("appartementId") appartementId: Long): List<Intervention>
}
