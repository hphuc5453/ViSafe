package hphuc.project.visafe_version1.vi_safe.screen.settings_role.presentation.model

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel

class SettingsRoleItemViewModel(
    val typeAccident: Int,
    val type: String,
    val name: String,
    var isChoose: Boolean = false
) : ViewModel