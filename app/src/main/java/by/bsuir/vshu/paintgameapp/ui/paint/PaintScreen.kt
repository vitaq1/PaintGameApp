package by.bsuir.vshu.paintgameapp.ui

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import by.bsuir.vshu.paintgameapp.R
import by.bsuir.vshu.paintgameapp.data.FigureRepository
import by.bsuir.vshu.paintgameapp.noRippleClickable
import by.bsuir.vshu.paintgameapp.ui.paint.PaintViewModel
import by.bsuir.vshu.paintgameapp.ui.theme.*
import by.bsuir.vshu.paintgameapp.ui.welcome.SettingSection
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController
import java.io.*
import kotlin.math.abs


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PaintScreen(
    navController: NavController,
    figureName: String,
    model: PaintViewModel = viewModel()
) {

    val visible = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    val accuracy = remember {
        mutableStateOf(0)
    }

    val context = LocalContext.current

    val figure = FigureRepository.getFigureByName(figureName)

    val controller = rememberDrawController()
    controller.apply {
        setStrokeColor(model.pressedColor)
        setStrokeWidth(40f)

    }

    val b1 = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        figure.pictureComplete
    )
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
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(0.dp))

                Text(text = "${accuracy.value} %", fontSize = 30.sp)

                Spacer(modifier = Modifier.width(0.dp))

                IconButton(onClick = {
                    accuracy.value = calculateAccuracy(b1, controller.getDrawBoxBitmap()!!)
                }, Modifier.size(50.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Search",
                        Modifier.scale(2f)
                    )
                }

                Spacer(modifier = Modifier.width(0.dp))

                IconButton(onClick = {

                    if (accuracy.value > 80) {
                        mToast("Вы прошли уровень!", context)
                        figure.progress = accuracy.value
                        FigureRepository.updateFigure(figure)
                        FigureRepository.updateData(context)
                    }
                    else  {
                        mToast("Слишком неточно!", context)
                    }

                }, Modifier.size(50.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "pass",
                        Modifier.scale(2f)
                    )
                }

                Spacer(modifier = Modifier.width(0.dp))

            }


            Box(

                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .padding(top = 30.dp, bottom = 40.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(DARK_WHITE, RoundedCornerShape(20.dp))
                    .border(5.dp, model.pressedColor, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center

            ) {


                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = figure.picture),
                        contentDescription = figure.name,
                        modifier = Modifier
                            .size(160.dp),
                        tint = figure.color
                    )
                    Icon(
                        painter = painterResource(id = figure.picture),
                        contentDescription = figure.name,
                        modifier = Modifier
                            .size(140.dp),
                        tint = WHITE
                    )

                }


                DrawBox(
                    drawController = controller,
                    modifier = Modifier
                        .fillMaxSize()

                )

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

private fun mToast(s: String, context: Context) {
    Toast.makeText(context, s, Toast.LENGTH_LONG).show()
}

fun calculateAccuracy(b1: Bitmap, b2: Bitmap): Int {

    val width = 914
    val height = 1232
    val step = 10
    var total = 0
    var equal = 0
    for (w in 0..width - 1 step step)
        for (h in 0..height - 1 step step) {
            var b1Col = b1.getColor(w, h)
            var b2Col = b2.getColor(w, h)
            if (b2Col.green() != 0f || b1Col.green() != 0f) {
                if (isColorsEquals(
                        b1Col.red(),
                        b1Col.green(),
                        b1Col.blue(),
                        b2Col.red(),
                        b2Col.green(),
                        b2Col.blue()
                    )
                ) equal++
                total++
            }

        }

    return ((equal.toDouble() / total.toDouble()) * 100).toInt()
}

fun isColorsEquals(r1: Float, g1: Float, b1: Float, r2: Float, g2: Float, b2: Float): Boolean {
    //if (r1 == 0f) return false
    return abs(r1 - r2) < 0.05 && abs(g1 - g2) < 0.05 && abs(b1 - b2) < 0.05
}


fun saveMediaToStorage(bitmap: Bitmap, context: Context) {
    val filename = "image.jpg"
    var fos: OutputStream? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
    }

    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
}