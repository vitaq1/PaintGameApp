package by.bsuir.vshu.paintgameapp.ui.menu

import android.util.Log
import android.widget.Space
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.bsuir.vshu.paintgameapp.R
import by.bsuir.vshu.paintgameapp.model.Figure
import by.bsuir.vshu.paintgameapp.noRippleClickable
import by.bsuir.vshu.paintgameapp.ui.theme.*
import by.bsuir.vshu.paintgameapp.ui.welcome.SettingSection
import kotlin.math.ceil


var expanded = 0

@Composable
fun MenuScreen(navController: NavController) {
    Log.i("Opening MenuScreen", "")

    val visible = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(YELLOW)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedVisibility(
                visible,
                enter = slideInVertically(spring(stiffness = Spring.StiffnessMediumLow))
                { fullHeight -> -2 * fullHeight },
                exit = slideOutVertically(spring(stiffness = Spring.StiffnessMediumLow))
                { fullHeight -> -2 * fullHeight }
            ) {
                SettingSection()
            }

            var figures: MutableList<Figure> = mutableListOf()
            figures.add(Figure("Rect", 70, R.drawable.ic_rect, GREEN))
            figures.add(Figure("Circle", 50, R.drawable.ic_circle, PURPLE))
            figures.add(Figure("Triangle", 0, R.drawable.ic_triangle, BLUE))
            figures.add(Figure("Cell", 0, R.drawable.ic_cell, RED))
            figures.add(Figure("Star", 0, R.drawable.ic_star, YELLOW))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                repeat(figures.size) {
                    item {
                        FigureCard(figures[it], it % 2 == 0)
                    }
                }

            }

        }


    }

}


@Composable
fun FigureCard(figure: Figure, isLeft: Boolean) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val cardWidth by animateDpAsState(if (expanded) 320.dp else 190.dp)



    Card(
        modifier = Modifier
            .padding(top = 40.dp)
            .height(190.dp)
            .width(cardWidth)
            .background(Color.Transparent), shape = RoundedCornerShape(30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(DARK_WHITE),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(0.dp))
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = figure.picture),
                    contentDescription = figure.name,
                    modifier = Modifier
                        .size(110.dp),
                    tint = figure.color
                )
                Icon(
                    painter = painterResource(id = figure.picture),
                    contentDescription = figure.name,
                    modifier = Modifier
                        .size(95.dp)
                        .noRippleClickable { expanded = !expanded },
                    tint = WHITE
                )
            }


            AnimatedVisibility(
                expanded,
                enter = fadeIn(tween(150)) + expandHorizontally(tween(150)),
                exit = fadeOut(tween(100)) + shrinkHorizontally(tween(100))
            ) {
                Column(
                    modifier = Modifier.height(110.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.width(0.dp))
                    Text(text = "PLAY", fontSize = 40.sp, color = figure.color)
                    Spacer(modifier = Modifier.width(0.dp))
                    Text(text = "${figure.progress} %", fontSize = 25.sp,)
                    Spacer(modifier = Modifier.width(0.dp))
                    GradientProgressbar(figure = figure)
                    Spacer(modifier = Modifier.width(0.dp))
                }
            }
            Spacer(modifier = Modifier.width(0.dp))


        }
    }

}

@Composable
fun GradientProgressbar(
    indicatorHeight: Dp = 20.dp,
    backgroundIndicatorColor: Color = Color.LightGray.copy(alpha = 0.5f),
    figure: Figure
) {


    Canvas(
        modifier = Modifier
            .width(140.dp)
            .height(indicatorHeight)
            .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp)
    ) {

        // Background indicator
        drawLine(
            color = backgroundIndicatorColor,
            cap = StrokeCap.Round,
            strokeWidth = size.height,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = 0f)
        )

        // Convert the downloaded percentage into progress (width of foreground indicator)
        val progress = (figure.progress / 100.0) * size.width // size.width returns the width of the canvas

        // Foreground indicator
        drawLine(
            brush = SolidColor(figure.color),
            cap = StrokeCap.Round,
            strokeWidth = size.height,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = progress.toFloat(), y = 0f)
        )

    }

}