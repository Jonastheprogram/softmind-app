package br.com.fiap.softmind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.softmind.Screens.WelcomeScreen
import br.com.fiap.softmind.Screens.*
import br.com.fiap.softmind.ui.theme.SoftMindTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.softmind.ui.ViewModel.FeelingViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoftMindTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}




@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val feelingViewModel: FeelingViewModel = viewModel()
    NavHost(navController = navController, startDestination = "welcomescreen") {

        composable("riskassessment") {

            RiskAssessmentScreen(
                navController = navController,
                viewModel = feelingViewModel
            )
        }

        composable("welcomescreen") { WelcomeScreen(navController) }

        composable("feelings") { FeelingScreen(navController, viewModel = feelingViewModel) }

        composable("moodhistory") { MoodHistoryScreen(navController) }
        composable("support") { SupportScreen(navController) }
        composable("statisticmood") { StatisticMoodScreen(navController) }
        composable("chatsup") { ChatScreen(navController) }

    }

}


