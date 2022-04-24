package by.bsuir.vshu.paintgameapp.ui.menu

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import by.bsuir.vshu.paintgameapp.ui.theme.YELLOW

@Composable
fun MenuScreen(navController: NavController) {
    Log.i("Opening MenuScreen", "")

    Column(modifier = Modifier
        .fillMaxSize()
        .background(YELLOW)) {

    }

}