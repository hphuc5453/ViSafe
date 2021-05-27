package hphuc.project.visafe_version1.core.base.domain.listener

@FunctionalInterface
interface OnWorking<out T> {
    fun work(): T
}
