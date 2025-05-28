package bts.sio.azurimmo.model

import android.content.ClipDescription
import java.util.Date



data class Intervention(
    val id: Int,
    val description: String,
    val typeInter: String,
    val dateInter: Date
)
