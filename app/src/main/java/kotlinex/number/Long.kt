package kotlinex.number

import hphuc.project.visafe_version1.core.app.util.DecimalUtils
import java.text.SimpleDateFormat
import java.util.*

fun Long?.getValueOrDefaultWithValue(value: Long): Long {
    return this ?: value
}

fun Long?.getValueOrDefaultIsZero(): Long {
    return this ?: 0
}

fun Long.formatTimeHHMMSS(): String {
    var second: Long = 0
    var minute: Long = 0
    var hour: Long = 0
    if (this > 0) {
        second = this / 1000
        minute = second / 60
        hour = minute / 60
        minute %= 60
        second %= 1000
    }
    return String.format(Locale.ENGLISH, "%02d:%02d:%02d", hour, minute, second)
}

fun Long.formatLongTime(format: String): String {
    return try {
        val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        dateFormat.format(this)
    } catch (e: Exception) {
        ""
    }
}

fun Long.formatTimeHHMM(): String {
    var minute: Long = 0
    var hour: Long = 0
    if (this > 0) {
        val second: Long = this / 1000
        minute = second / 60
        hour = minute / 60
        minute %= 60
    }

    return String.format(Locale.ENGLISH, "%02d:%02d", hour, minute)
}

fun Long.formatNumber(): String {
    return DecimalUtils.decimalFormat(this.toDouble(), 0, Locale.ENGLISH, DecimalUtils.Companion.GroupDecimalMode.COMMA, DecimalUtils.Companion.AfterDotMode.IF_EXIST)
}
