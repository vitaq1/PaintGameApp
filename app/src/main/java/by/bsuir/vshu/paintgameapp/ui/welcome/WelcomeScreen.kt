package by.bsuir.vshu.paintgameapp.ui.welcome

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.bsuir.vshu.paintgameapp.R
import by.bsuir.vshu.paintgameapp.noRippleClickable
import by.bsuir.vshu.paintgameapp.ui.menu.MenuScreen
import by.bsuir.vshu.paintgameapp.ui.theme.*


@OptIn(
    ExperimentalComposeUiApi::class, androidx.compose.animation.ExperimentalAnimationApi::class,
    androidx.compose.animation.core.ExperimentalTransitionApi::class
)
@Composable
fun WelcomeScreen(navController: NavController) {

    Log.i("Opening WelcomeScreen", "")
    val visible = remember {
        MutableTransitionState(true).apply {
            targetState = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GREEN),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        AnimatedVisibility(
            visible,
            enter = slideInVertically(
                spring(
                    stiffness = Spring.StiffnessMediumLow,
                )
            ) { fullHeight -> -2 * fullHeight },
            exit = slideOutVertically(
                spring(
                    stiffness = Spring.StiffnessMediumLow,
                )
            ) { fullHeight -> -2 * fullHeight }
        ) {
            SettingSection()
        }
        AnimatedVisibility(
            visible,
            enter = slideInHorizontally(
                spring(
                    stiffness = Spring.StiffnessMediumLow,
                )
            ) { fullWidth -> 2 * fullWidth },
            exit = slideOutHorizontally(
                spring(
                    stiffness = Spring.StiffnessMediumLow,
                )
            ) { fullWidth -> 2 * fullWidth }
        ) {
            Text(
                text = "Draw it",
                fontSize = 60.sp,
                color = WHITE,
                modifier = Modifier.padding(top = 200.dp)
            )
        }

        val scaleAnim by animateFloatAsState(if (!visible.targetState) 25f else 1f)

        Spacer(modifier = Modifier.height(50.dp))

        Box(
            modifier = Modifier
                .scale(scaleAnim)
                .height(200.dp)
                .width(200.dp)
                .noRippleClickable {
                    visible.targetState = !visible.targetState
                }

        ) {
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = "play",
                modifier = Modifier.fillMaxSize(),
                tint = YELLOW,
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }

    val needNavigate = visible.isIdle && !visible.currentState
    LaunchedEffect(needNavigate) {
        if (needNavigate) {
            navController.navigate("menu")
        }
    }
}

@Composable
fun SettingSection(){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(end = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {

        Button(
            onClick = {},
            modifier = Modifier
                .height(52.dp)
                .width(52.dp)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(backgroundColor = WHITE)
        ) {
            Image(
                painterResource(id = R.drawable.ic_sound),
                contentDescription = "sound",
                modifier = Modifier.scale(2f),
                colorFilter = ColorFilter.tint(PURPLE)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor = WHITE)
        ) {
            Image(
                painterResource(id = R.drawable.ic_music),
                contentDescription = "music",
                modifier = Modifier.scale(2f),
                colorFilter = ColorFilter.tint(BLUE)
            )
        }

    }

}