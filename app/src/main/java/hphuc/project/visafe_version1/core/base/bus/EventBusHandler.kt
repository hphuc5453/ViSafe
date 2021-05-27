package hphuc.project.visafe_version1.core.base.bus

import hphuc.project.visafe_version1.core.base.bus.EventBusData

interface EventBusHandler {
    fun onReceiveEvent(data: EventBusData)
}