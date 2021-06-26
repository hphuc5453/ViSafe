package hphuc.project.visafe_version1.vi_safe.screen.main.data

import hphuc.project.visafe_version1.core.base.bus.EventBusData

class EventMenu(
    var event: CategoryType? = null,
    var eventAction: ActionType? = null,
    var eventChildFragment: ChildFragment? = null,
    var data: Any? = null
): EventBusData{
    enum class CategoryType {
        HOME, CONTACTS, LIVE, CALL, SETTINGS
    }

    enum class ActionType {
          SHOW_LOADING, HIDE_LOADING, BACK_COUNT
    }

    enum class ChildFragment {
       HOME_MAP, NOTIFY, SETTINGS_ROLE
    }
}
