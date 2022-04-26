package by.bsuir.vshu.paintgameapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import by.bsuir.vshu.paintgameapp.model.Point
import by.bsuir.vshu.paintgameapp.noRippleClickable
import by.bsuir.vshu.paintgameapp.ui.paint.PaintViewModel
import by.bsuir.vshu.paintgameapp.ui.theme.*
import by.bsuir.vshu.paintgameapp.ui.welcome.SettingSection

@Composable
fun PaintScreen(navController: NavController, model: PaintViewModel = viewModel()) {

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

            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .padding(top = 20.dp)
                    .background(DARK_WHITE, RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .height(90.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                item { Spacer(modifier = Modifier.width(0.dp)) }

                item {
                    ColorButton(color = RED, model)
                }
                item {
                    ColorButton(color = GREEN, model)
                }
                item {
                    ColorButton(color = BLUE, model)
                }
                item {
                    ColorButton(color = YELLOW, model)
                }
                item {
                    ColorButton(color = PURPLE, model)
                }

                item { Spacer(modifier = Modifier.width(0.dp)) }


            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .padding(top = 30.dp)
                    .fillMaxWidth()
                    .height(50.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Spacer(modifier = Modifier.width(0.dp))

                Text(text = "${model.currentProgress} %", fontSize = 30.sp)

                Spacer(modifier = Modifier.width(0.dp))

            }

            var offsetX by remember { mutableStateOf(0f) }
            var offsetY by remember { mutableStateOf(0f) }
            var pointList = remember { mutableStateListOf<Point>() }
            Box(

                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .padding(top = 30.dp, bottom = 40.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(DARK_WHITE, RoundedCornerShape(20.dp))
                    .border(5.dp, model.pressedColor, RoundedCornerShape(20.dp))
                    .pointerInput(Unit) {

                        detectDragGestures { change, offset ->
                            println(change.position)
                            change.consumeAllChanges()
                            offsetX += offset.x
                            offsetY += offset.y
                            pointList.add(Point(offsetX, offsetY, model.pressedColor))
                            println("dragging")
                        }
                    }
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    for (point in pointList) {
                        drawCircle(point.color, 5f, Offset(point.x, point.y))
                    }

                }

            }

        }
    }


}

@Composable
fun ColorButton(color: Color, model: PaintViewModel) {

    val scale by animateFloatAsState(if (color == model.pressedColor) 1.15f else 1f)

    Card(
        modifier = Modifier
            .size(55.dp)
            .scale(scale)
            .noRippleClickable {
                model.pressedColor = color
                println(model.pressedColor)
            },
        shape = RoundedCornerShape(15.dp),
        backgroundColor = color
    ) {}

}