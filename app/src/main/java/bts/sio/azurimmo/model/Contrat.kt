package bts.sio.azurimmo.model

import java.util.Date

data class Contrat(
    val id: Int,
    val dateEntree: String,
    val dateSortie: String,
    val montantLoyer: Float,
    val montantCharges: Float,
    val statut: String,
    val appartement: Appartement,
    val locataire: Locataire
)

