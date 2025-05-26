package bts.sio.azurimmo.views

import AppartementViewModel
import ContratList
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.views.appartement.*
import bts.sio.azurimmo.views.batiment.BatimentAdd
import bts.sio.azurimmo.views.batiment.BatimentList
import bts.sio.azurimmo.views.contrat.EditContrat
import ContratViewModel

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {

        // Accueil
        composable("home") {
            HomeScreen(navController)
        }

        // ---------- Bâtiments ----------
        composable("batiment_list") {
            BatimentList(
                onBatimentClick = { batimentId ->
                    navController.navigate("batiment_appartements_list/$batimentId")
                },
                onAddBatimentClick = {
                    navController.navigate("add_batiment")
                }
            )
        }

        composable("add_batiment") {
            BatimentAdd(onBatimentAdd = {
                navController.popBackStack()
            })
        }

        // ---------- Appartements ----------
        composable(
            "batiment_appartements_list/{batimentId}",
            arguments = listOf(navArgument("batimentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val batimentId = backStackEntry.arguments?.getInt("batimentId") ?: return@composable
            AppartementList(
                navController = navController,
                batimentId = batimentId,
                viewModel = viewModel(),
                onAddAppartementClick = {
                    navController.navigate("add_appartement/$batimentId")
                }
            )
        }

        composable(
            "add_appartement/{batimentId}",
            arguments = listOf(navArgument("batimentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val batimentId = backStackEntry.arguments?.getInt("batimentId") ?: return@composable
            AppartementAdd(
                onAddAppartement = { navController.popBackStack() },
                batimentId = batimentId
            )
        }

        composable("appartement_list") {
            AppartementListGlobal(onAddClick = { navController.navigate("add_appartement_global") })
        }

        composable("add_appartement_global") {
            AppartementAddGlobal(onAppartementAdded = { navController.popBackStack() })
        }

        composable(
            "edit_appartement/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val appartement = viewModel<AppartementViewModel>().appartements.value.find { it.id == id }
            if (appartement != null) {
                EditAppartement(appartement = appartement, onUpdated = { navController.popBackStack() })
            } else {
                Text("Appartement introuvable")
            }
        }

        // ---------- Contrats ----------
        composable("contrat_list") {
            ContratList(navController = navController)
        }

        composable(
            "edit_contrat/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val contrat = viewModel<ContratViewModel>().contrats.value.find { it.id == id }
            if (contrat != null) {
                EditContrat(contrat = contrat, onUpdated = { navController.popBackStack() })
            } else {
                Text("Contrat introuvable")
            }
        }
    }
}
