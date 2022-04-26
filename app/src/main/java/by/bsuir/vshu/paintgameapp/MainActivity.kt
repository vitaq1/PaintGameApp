package by.bsuir.vshu.paintgameapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import by.bsuir.vshu.paintgameapp.ui.PaintScreen

import com.google.accompanist.navigation.animation.composable

import by.bsuir.vshu.paintgameapp.ui.menu.MenuScreen
import by.bsuir.vshu.paintgameapp.ui.splash.SplashScreen
import by.bsuir.vshu.paintgameapp.ui.theme.PaintGameAppTheme
import by.bsuir.vshu.paintgameapp.ui.welcome.WelcomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            PaintGameAppTheme() {
                val navController = rememberAnimatedNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "paint",
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None },
                    ) {
                        composable("splash") { SplashScreen(navController) }
                        composable("welcome") { WelcomeScreen(navController) }
                        composable("menu") { MenuScreen(navController) }
                        composable("paint") { PaintScreen(navController) }
                    }
                }
            }
        }
    }
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
