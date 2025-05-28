package bts.sio.azurimmo.model

import android.content.ClipDescription
import java.util.Date

data class Paiement(
    val id: Int? = null,
    val montant: Float? = null,
    val datePaiement: String? = null,
    val contrat: Contrat? = null
)


