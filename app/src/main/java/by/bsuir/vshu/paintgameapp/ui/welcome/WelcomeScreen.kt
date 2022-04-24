package by.bsuir.vshu.paintgameapp.ui.welcome

import android.view.MotionEvent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.vshu.paintgameapp.R
import by.bsuir.vshu.paintgameapp.ui.theme.*
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun WelcomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GREEN),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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
        Spacer(modifier = Modifier.height(200.dp))
        Text(text = "Draw it", fontSize = 40.sp, color = WHITE)
        Spacer(modifier = Modifier.height(50.dp))

        val selected = remember { mutableStateOf(false) }
        val scale = animateFloatAsState(if (selected.value) 1.3f else 1f)

            IconButton(
                onClick = {  },
                modifier = Modifier
                    .scale(scale.value)
                    .height(200.dp)
                    .width(200.dp)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected.value = true }

                            MotionEvent.ACTION_UP  -> {
                                selected.value = false }
                        }
                        true
                    }
            ){
                Image(
                    painterResource(id = R.drawable.ic_play),
                    contentDescription = "play",
                    modifier = Modifier.fillMaxSize(),
                    colorFilter = ColorFilter.tint(YELLOW)
                )
            }
    }


}