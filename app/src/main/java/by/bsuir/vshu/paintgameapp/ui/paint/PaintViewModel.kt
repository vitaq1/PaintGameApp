package by.bsuir.vshu.paintgameapp.ui.paint

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import by.bsuir.vshu.paintgameapp.ui.theme.RED

class PaintViewModel () : ViewModel() {

    var pressedColor by mutableStateOf(RED)
    var currentProgress by mutableStateOf(0)

}