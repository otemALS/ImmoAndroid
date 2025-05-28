package bts.sio.azurimmo.views

import AppartementViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import bts.sio.azurimmo.viewmodel.ContratViewModel
import bts.sio.azurimmo.viewmodel.PaiementViewModel
import bts.sio.azurimmo.viewsmodel.LocataireViewModel
import bts.sio.azurimmo.views.appartement.*
import bts.sio.azurimmo.views.batiment.BatimentAdd
import bts.sio.azurimmo.views.batiment.BatimentList
import bts.sio.azurimmo.views.contrat.EditContrat
import bts.sio.azurimmo.views.contrat.AddContrat
import bts.sio.azurimmo.views.contrat.ContratList
import bts.sio.azurimmo.views.locataire.AddLocataire
import bts.sio.azurimmo.views.locataire.EditLocataire
import bts.sio.azurimmo.views.locataire.LocataireList
import bts.sio.azurimmo.views.paiement.AddPaiement
import bts.sio.azurimmo.views.paiement.EditPaiement
import bts.sio.azurimmo.views.paiement.PaiementList



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
            AppartementListGlobal(
                navController = navController,
                onAddClick = { navController.navigate("add_appartement_global") }
            )
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

        composable("locataire_list") {
            LocataireList(navController = navController)
        }

        composable(
            "edit_locataire/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val viewModel: LocataireViewModel = viewModel()
            val locataire = viewModel.locataires.value.find { it.id == id }

            if (locataire != null) {
                EditLocataire(
                    locataire = locataire,
                    onUpdated = {
                        viewModel.getLocataires() // ✅ Recharge la liste après modification
                        navController.popBackStack() // ✅ Retour à la liste
                    }
                )
            } else {
                Text("Locataire introuvable")
            }
        }

        composable("add_locataire") {
            AddLocataire(onLocataireAdded = {
                navController.popBackStack()
            })
        }

        composable("edit_locataire/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val locataire = viewModel<LocataireViewModel>().locataires.value.find { it.id == id }
            if (locataire != null) {
                EditLocataire(locataire = locataire, onUpdated = { navController.popBackStack() })
            } else {
                Text("Locataire introuvable")
            }
        }

        composable("add_contrat") {
            AddContrat(onContractAdded = { navController.popBackStack() })

        }

        composable("edit_contrat/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            val contrat = viewModel<ContratViewModel>().contrats.value.find { it.id == id }
            if (contrat != null) {
                EditContrat(contrat = contrat) {
                    navController.popBackStack()
                }
            } else {
                Text("Contrat introuvable")
            }
        }
        composable("paiement_list") {
            PaiementList(
                navController = navController,
                onAddClick = { navController.navigate("add_paiement") }
            )
        }

        composable("add_paiement") {
            AddPaiement(onPaiementAdded = { navController.popBackStack() })
        }
        composable(
            "edit_paiement/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val paiementViewModel: PaiementViewModel = viewModel()

            // Charger les paiements
            LaunchedEffect(Unit) {
                paiementViewModel.getPaiements()
            }

            val paiement = paiementViewModel.paiements.value.find { it.id == id }
            if (paiement != null) {
                EditPaiement(
                    paiement = paiement,
                    viewModel = paiementViewModel,
                    onUpdated = { navController.popBackStack() }
                )
            } else {
                Text("Paiement introuvable")
            }
        }






    }


}
