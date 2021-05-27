package hphuc.project.visafe_version1.core.base.domain.listener

@FunctionalInterface
interface OnWorkingWithException<out T> {
    @Throws(Exception::class)
    fun work(): T
}
