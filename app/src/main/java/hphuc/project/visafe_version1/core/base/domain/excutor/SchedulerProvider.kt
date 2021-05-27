package hphuc.project.visafe_version1.core.base.domain.excutor

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun createScheduler() : Scheduler
}