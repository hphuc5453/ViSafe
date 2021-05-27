package kotlinex.number

import hphuc.project.visafe_version1.core.app.util.DecimalUtils
import java.util.*

fun Double?.getValueOrDefaultWithValue(value: Double): Double {
    return this ?: value
}

fun Double?.getValueOrDefaultIsZero(): Double {
    return this ?: 0.0
}

fun Double.formatNumber(): String {
    return DecimalUtils.decimalFormat(this, 2, Locale.ENGLISH, DecimalUtils.Companion.GroupDecimalMode.COMMA, DecimalUtils.Companion.AfterDotMode.IF_EXIST)
}

fun Double.roundUp(numberDigit: Int): Double {
    return DecimalUtils.roundNumber(this, numberDigit, DecimalUtils.Companion.RoundMode.UP)
}

fun Double.roundDown(numberDigit: Int): Double {
    return DecimalUtils.roundNumber(this, numberDigit, DecimalUtils.Companion.RoundMode.DOWN)
}

fun Double.roundNatural(numberDigit: Int): Double {
    return DecimalUtils.roundNumber(this, numberDigit, DecimalUtils.Companion.RoundMode.NATURAL)
}