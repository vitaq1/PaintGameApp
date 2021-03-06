package by.bsuir.vshu.paintgameapp.ui.splash

import android.util.Log
import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.bsuir.vshu.paintgameapp.R
import by.bsuir.vshu.paintgameapp.ui.theme.GREEN
import by.bsuir.vshu.paintgameapp.ui.welcome.WelcomeScreen
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun SplashScreen(navController: NavController) {

    Log.i("Opening SplashScreen", "")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GREEN)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
        val progress by animateLottieCompositionAsState(composition)

        LaunchedEffect(progress) {
            if (progress >= 1f) {
                navController.navigate("welcome")
            }
        }

        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.align(Alignment.Center)
        )


    }

}