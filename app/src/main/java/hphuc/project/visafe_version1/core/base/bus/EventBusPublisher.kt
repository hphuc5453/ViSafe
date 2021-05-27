package hphuc.project.visafe_version1.core.base.bus

import hphuc.project.visafe_version1.core.base.bus.EventBusData

interface EventBusPublisher {
    fun publishEvent(data: EventBusData)
}
