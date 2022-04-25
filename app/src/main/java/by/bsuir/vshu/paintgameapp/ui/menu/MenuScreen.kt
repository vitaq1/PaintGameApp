package by.bsuir.vshu.paintgameapp.ui.menu

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.bsuir.vshu.paintgameapp.R
import by.bsuir.vshu.paintgameapp.model.Figure
import by.bsuir.vshu.paintgameapp.ui.theme.*
import by.bsuir.vshu.paintgameapp.ui.welcome.SettingSection
import kotlin.math.ceil

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

        Column(modifier = Modifier.fillMaxSize()) {

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
            figures.add(Figure("Rect", 0, R.drawable.ic_rect, GREEN))
            figures.add(Figure("Circle", 0, R.drawable.ic_circle, PURPLE))
            figures.add(Figure("Triangle", 0, R.drawable.ic_triangle, BLUE))
            figures.add(Figure("Cell", 0, R.drawable.ic_cell, RED))
            figures.add(Figure("Star", 0, R.drawable.ic_star, YELLOW))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                repeat(ceil(figures.size / 2.0).toInt()) {
                    item {
                        LazyRow(
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 20.dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            item { Spacer(modifier = Modifier.width(1.dp)) }
                            item {
                                FigureCard(figure = figures[it * 2])
                            }
                            if (it * 2 + 1 < figures.size) {
                                item {
                                    FigureCard(figure = figures[it * 2 + 1])
                                }
                            }
                            item { Spacer(modifier = Modifier.width(1.dp)) }
                        }
                    }
                }
            }

        }


    }

}

@Composable
fun FigureCard(figure: Figure) {
    Card(
        modifier = Modifier
            .size(140.dp)
            .background(Color.Transparent), shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(DARK_WHITE),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = figure.picture),
                    contentDescription = figure.name,
                    modifier = Modifier
                        .size(90.dp),
                    tint = figure.color
                )
                Icon(
                    painter = painterResource(id = figure.picture),
                    contentDescription = figure.name,
                    modifier = Modifier
                        .size(80.dp),
                    tint = WHITE
                )
            }

        }
    }
}