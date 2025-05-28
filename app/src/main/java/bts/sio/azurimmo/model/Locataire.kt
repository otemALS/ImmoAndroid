package bts.sio.azurimmo.model



data class Locataire(
    val id: Int? = null,
    val nom: String,
    val prenom: String,
    val dateN: String,
    val lieuN: String
)
