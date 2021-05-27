package hphuc.project.visafe_version1.core.app.domain.excecutor

import hphuc.project.visafe_version1.core.base.domain.listener.*


class EventFireUtil {
    companion object {
        @JvmStatic
        @Throws(Exception::class)
        fun <P, R> fireEvent(event: OnWorkingWithParam<P, R>?, param: P): R? {
            return event?.doWork(param)
        }

        @JvmStatic
        fun <T> fireEvent(event: OnActionDataWithPos<T>?, data: T, pos: Int) {
            event?.onAction(data, pos)
        }

        @JvmStatic
        fun <T> fireEvent(event: OnActionData<T>?, data: T) {
            event?.onAction(data) // lấy thằng acction và bấm nút lại thôi
        }

        @JvmStatic
        fun fireEvent(event: OnActionNotify?) {
            event?.onActionNotify()
        }

        @JvmStatic
        @Throws(Exception::class)
        fun <T> fireEvent(event: OnWorkingWithException<T>?): T? {
            return event?.work()
        }

        @JvmStatic
        fun <T> fireEvent(event: OnWorking<T>?): T? {
            return event?.work()
        }

        @JvmStatic
        fun fireEvent(executable: Executable?) {
            executable?.execute()
        }
    }
}