package kotlinex.boolean

fun Boolean?.getValueOrDefault(): Boolean {
    return this ?: false
}