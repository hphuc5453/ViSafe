package hphuc.project.visafe_version1.core.base.bus

import hphuc.project.visafe_version1.core.base.bus.EventBusData
import hphuc.project.visafe_version1.core.base.bus.EventBusHandler
import hphuc.project.visafe_version1.core.base.bus.EventBusManager
import java.util.concurrent.CopyOnWriteArrayList

class SimpleEventBusManager : EventBusManager {
    private val handlers = CopyOnWriteArrayList<EventBusHandler>()

    override fun register(handler: EventBusHandler) {
        if (!this.handlers.contains(handler)) {
            this.handlers.add(handler)
        }
    }

    override fun unregister(handler: EventBusHandler) {
        this.handlers.remove(handler)
    }

    override fun publishEvent(data: EventBusData) {
        for (handler in handlers) {
            handler?.onReceiveEvent(data)
        }
    }
}
