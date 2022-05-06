package by.bsuir.vshu.paintgameapp.data

import android.app.Activity
import android.content.Context
import by.bsuir.vshu.paintgameapp.R
import by.bsuir.vshu.paintgameapp.model.Figure
import by.bsuir.vshu.paintgameapp.ui.theme.*


object FigureRepository {

    private var figures: MutableList<Figure> = mutableListOf(
        Figure("rect", 0, R.drawable.ic_rect, R.drawable.rect, GREEN),
        Figure("circle", 0, R.drawable.ic_circle, R.drawable.circle, PURPLE),
        Figure("triangle", 0, R.drawable.ic_triangle, R.drawable.triangle, BLUE),
        Figure("cell", 0, R.drawable.ic_cell, R.drawable.cell, RED),
        Figure("star", 0, R.drawable.ic_star, R.drawable.star, YELLOW)
    )

    fun getFigureByName(name: String): Figure {
        return figures.filter { it.name == name }[0]
    }

    fun updateFigure(figure: Figure) {
        val index = figures.indexOfFirst { it.name == figure.name }
        figures.removeIf { it.name == figure.name }
        figures.add(index, figure)
    }

    fun isOpen(figureName: String): Boolean {
        val figure = getFigureByName(figureName)

        if (figure.name == "rect") return true
        if (figure.name == "circle" && figures[0].progress > 80) return true
        if (figure.name == "triangle" && figures[1].progress > 80) return true
        if (figure.name == "cell" && figures[2].progress > 80) return true
        if (figure.name == "star" && figures[3].progress > 80) return true
        return false
    }

    fun loadData(context: Context) {
        val sharedPref = context.getSharedPreferences(
            "data", Context.MODE_PRIVATE
        )
        for (fig in figures){
            fig.progress = sharedPref.getInt(fig.name, 0)
        }

    }

    fun updateData(context: Context) {
        val sharedPref = context.getSharedPreferences(
            "data", Context.MODE_PRIVATE
        )
        for (fig in figures){
            with (sharedPref.edit()) {
                putInt(fig.name, fig.progress)
                apply()
            }
        }

    }


}