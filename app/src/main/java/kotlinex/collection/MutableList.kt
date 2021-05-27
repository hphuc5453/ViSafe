package kotlinex.collection

fun <T> MutableList<T>?.getValueOrDefault(): MutableList<T> {
    return this ?: mutableListOf()
}