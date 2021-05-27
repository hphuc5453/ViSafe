package hphuc.project.visafe_version1.core.app.domain.excecutor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import hphuc.project.visafe_version1.core.base.domain.excutor.SchedulerProvider

class AndroidPostTaskSchedulerProvider : SchedulerProvider {
    override fun createScheduler(): Scheduler = AndroidSchedulers.mainThread()
}