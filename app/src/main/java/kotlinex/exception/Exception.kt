package kotlinex.exception

import hphuc.project.visafe_version1.core.base.domain.exception.AppException

fun Exception.reThrow(errorMessage: String, errorCode: Int = 0) {
    throw AppException(errorMessage, errorCode, this)
}