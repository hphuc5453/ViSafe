package hphuc.project.visafe_version1.core.base.bus

interface EventBusManager : EventBusPublisher {
    fun register(handler: EventBusHandler)
    fun unregister(handler: EventBusHandler)
}