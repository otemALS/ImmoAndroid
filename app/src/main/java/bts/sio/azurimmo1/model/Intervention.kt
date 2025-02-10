package bts.sio.azurimmo1.model

data class Intervention(
    val id: Long,
    val date: String,
    val description: String,
    val realisePar: String,
    val appartement: Appartement,
    val entreprise: Entreprise
)
