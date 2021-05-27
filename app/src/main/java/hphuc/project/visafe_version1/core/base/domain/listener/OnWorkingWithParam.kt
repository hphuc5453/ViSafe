package hphuc.project.visafe_version1.core.base.domain.listener
@FunctionalInterface
interface OnWorkingWithParam<in P, out R> {
    fun doWork(param: P): R
}
