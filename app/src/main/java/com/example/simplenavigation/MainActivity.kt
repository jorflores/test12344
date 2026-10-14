package com.example.simplenavigation

import android.os.Bundle
import android.transition.TransitionManager.go
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplenavigation.ui.theme.SimpleNavigationTheme
import kotlinx.serialization.Serializable

/*
sealed class Screen(val route: String) {
    data object A : Screen("screenA")
    data object B : Screen("screenB")
    data object C : Screen("screenC")

}*/

// Las rutas ahora son tipos, no strings
@Serializable
sealed interface Screen {
    @Serializable
    data object A : Screen
    @Serializable
    data object B: Screen   // ruta con argumento
    @Serializable
    data object C : Screen
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleNavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationTest(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationTest(modifier: Modifier = Modifier) {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Screen.A) {
        composable<Screen.A> {
            ScreenA(goToB =  dropUnlessResumed { nav.navigate(Screen.B) },
                goToC = dropUnlessResumed  { nav.navigate(Screen.C) }
            )
        }
        composable<Screen.B> {
            ScreenB(
                onBack = dropUnlessResumed { nav.popBackStack() },
                goToC = dropUnlessResumed { nav.navigate(Screen.C) }
            )
        }
        composable<Screen.C> {
            ScreenC(onBack = dropUnlessResumed { nav.popBackStack() })
        }
    }
}




@Preview(showBackground = true)
@Composable
fun ScreenA(goToB: () -> Unit = {}, goToC: () -> Unit = {}) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Pantalla A", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Button(onClick = goToB) { Text("Ir a B") }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = goToC) { Text("Ir a C") }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenB(onBack: () -> Unit = {}, goToC: () -> Unit = {}) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Pantalla B", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onBack) { Text("Atrás") }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = goToC) { Text("Ir a C") }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenC(onBack: () -> Unit = {}) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Pantalla C", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onBack) { Text("Atrás") }
    }
}