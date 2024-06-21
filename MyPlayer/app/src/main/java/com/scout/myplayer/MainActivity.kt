package com.scout.myplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.scout.myplayer.ui.player.PlayerScreen
import com.scout.myplayer.viewmodel.PlayerViewModel
import com.scout.myplayer.ui.theme.MyPlayerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyPlayerTheme {
                Scaffold { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        PlayerApp()
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.PLAYER) {
        composable(route = Route.PLAYER) {
            PlayerDestination()
        }
    }
}

@Composable
private fun PlayerDestination() {
    val viewModel : PlayerViewModel = hiltViewModel()
    PlayerScreen(viewModel)
}

object Route {
    const val PLAYER = "player"
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyPlayerTheme {
        PlayerApp()
    }
}