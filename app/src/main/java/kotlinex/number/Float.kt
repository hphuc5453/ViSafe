package kotlinex.number

fun Float?.getValueOrDefaultWithValue(value: Float): Float {
    return this ?: value
}
fun Float?.getValueOrDefaultIsZero(): Float {
    return this ?: 0f
}