package hphuc.project.visafe_version1.vi_safe.screen.settings.presentation.model

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel

class SettingsMethodItemViewModel(
    val name: String,
    val icon: Int,
    val type: Int
) : ViewModel{
    enum class Type(val value: Int){
        ROLES(1), ALARM(2), REGISTER(3), RATE(4), HELP(5), LOGOUT(6)
    }
}