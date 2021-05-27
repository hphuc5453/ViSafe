package hphuc.project.visafe_version1.core.app.domain.excecutor

import hphuc.project.visafe_version1.core.base.domain.interactor.UseCaseExecution

class AndroidUseCaseExecution : UseCaseExecution(AndroidTaskSchedulerProvider().createScheduler(), AndroidPostTaskSchedulerProvider().createScheduler())