package hphuc.project.visafe_version1.core.app.domain.excecutor

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import hphuc.project.visafe_version1.core.base.domain.excutor.SchedulerProvider

class AndroidTaskSchedulerProvider : SchedulerProvider {
    override fun createScheduler(): Scheduler = Schedulers.from(TaskExecutor.get())
}