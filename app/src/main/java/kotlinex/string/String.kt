package kotlinex.string

import hphuc.project.visafe_version1.core.app.util.AccentRemover
import java.text.SimpleDateFormat
import java.util.*

fun String.calDiffTimeMilliToNow(format: String): Long {
    return try {
        val timeMilliSecondDateCurrent: Long = Calendar.getInstance(Locale.US).timeInMillis
        val timeMilliSecondDate = this.toDate(format).time
        timeMilliSecondDateCurrent - timeMilliSecondDate
    } catch (e: Exception) {
        0
    }
}

fun String.calDiffTimeMilliToDate(date: String, format: String): Long {
    try {
        Date()
        val timeMilliSecondDate = this.toDate(format).time
        val timeMilliSecondDateToDate: Long = date.toTimeMilliSecond(format)
        return timeMilliSecondDateToDate - timeMilliSecondDate
    } catch (e: Exception) {
    }
    return 0
}

fun String.toTimeMilliSecond(format: String): Long = this.toDate(format).time

fun String.removeAccentAndSpace(): String = AccentRemover.removeAccentAndSpace(this)

fun String?.getValueOrDefaultIsEmpty(): String = this ?: ""

fun String?.getValueOrDefaultInput(valueDefault: String): String = this ?: valueDefault

fun String.toDate(format: String): Date = SimpleDateFormat(format, Locale.US).parse(this)