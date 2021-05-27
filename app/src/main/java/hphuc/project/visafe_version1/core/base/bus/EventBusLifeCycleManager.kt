package hphuc.project.visafe_version1.core.base.bus

class EventBusLifeCycleManager {
    companion object {
        private var eventManager: SimpleEventBusManager? = null

        fun getInstance(): SimpleEventBusManager? {
            if (null == eventManager) {
                eventManager = SimpleEventBusManager()
            }
            return eventManager
        }
    }
}