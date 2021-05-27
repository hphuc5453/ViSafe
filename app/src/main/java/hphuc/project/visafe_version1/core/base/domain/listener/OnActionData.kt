package hphuc.project.visafe_version1.core.base.domain.listener

import java.io.Serializable

@FunctionalInterface
interface OnActionData<in T> : Serializable {
    fun onAction(data: T)
}
