package hphuc.project.visafe_version1.vi_safe.app.util.view.bubble_layout

enum class ArrowDirection(val value: Int) {
    LEFT(0), RIGHT(1), TOP(2), BOTTOM(3),  //CENTER
    LEFT_CENTER(4), RIGHT_CENTER(5), TOP_CENTER(6), BOTTOM_CENTER(7);

    companion object {
        fun fromInt(value: Int): ArrowDirection {
            for (arrowDirection in values()) {
                if (value == arrowDirection.value) {
                    return arrowDirection
                }
            }
            return LEFT
        }
    }

}