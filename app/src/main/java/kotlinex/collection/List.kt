package kotlinex.collection

fun <T> List<T>?.getValueOrDefault(): List<T> {
    return this ?: mutableListOf()
}