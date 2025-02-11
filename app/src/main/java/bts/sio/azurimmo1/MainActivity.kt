package bts.sio.azurimmo1

import AppartementList
import BatimentList
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import bts.sio.azurimmo1.ui.theme.Azurimmo1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Appel de la fonction principale pour structurer l'affichage
            AppartementList()
        }
    }

    // Fonction pour prévisualiser BatimentList uniquement
    @Preview(showBackground = true)
    @Composable
    fun PreviewBatimentList() {
        BatimentList()
    }

    // Fonction pour prévisualiser AppartementList uniquement
    @Preview(showBackground = true)
    @Composable
    fun PreviewAppartementList() {
        AppartementList()
    }
}

