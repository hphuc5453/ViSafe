package hphuc.project.visafe_version1.core.base.domain.exception

class AppException(val errorMessage: String, val errorCode: Int = 1, cause: Throwable? = null) : Exception(errorMessage, cause)