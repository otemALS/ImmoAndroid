package bts.sio.azurimmo.views

import AppartementViewModel
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

        // Liste des bâtiments
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

        // Formulaire ajout bâtiment
        composable("add_batiment") {
            BatimentAdd(onBatimentAdd = {
                navController.popBackStack()
            })
        }

        // Liste des appartements par bâtiment
        composable(
            route = "batiment_appartements_list/{batimentId}",
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

        // Ajout appartement à un bâtiment
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

        // Liste globale des appartements
        composable("appartement_list") {
            AppartementListGlobal(onAddClick = { navController.navigate("add_appartement_global") })
        }

        // Formulaire d'ajout global
        composable("add_appartement_global") {
            AppartementAddGlobal(onAppartementAdded = { navController.popBackStack() })
        }

        // ✏️ Modification d’un appartement
        composable(
            "edit_appartement/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val appartement = viewModel<AppartementViewModel>().appartements.value.find { it.id == id }
            if (appartement != null) {
                EditAppartement(
                    appartement = appartement,
                    onUpdated = { navController.popBackStack() }
                )
            } else {
                Text("Appartement introuvable")
            }
        }
    }
}
