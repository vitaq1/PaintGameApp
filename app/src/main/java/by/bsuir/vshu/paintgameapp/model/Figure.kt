package by.bsuir.vshu.paintgameapp.model

import androidx.compose.ui.graphics.Color
import by.bsuir.vshu.paintgameapp.ui.theme.WHITE

data class Figure(
    var name: String = "",
    var progress: Int = 0,
    var picture: Int = 0,
    var color: Color = WHITE,
    ) {
}