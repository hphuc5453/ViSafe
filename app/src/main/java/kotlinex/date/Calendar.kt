package kotlinex.date

import java.util.*


fun Calendar?.getValueOrDefaultIsNow(): Calendar {
    return this ?: Calendar.getInstance()
}