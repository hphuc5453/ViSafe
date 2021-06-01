package hphuc.project.visafe_version1.vi_safe.screen.sign_up.presentation.model

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel

class RoleItemViewModel(
    val type: String,
    val name: String,
    var isChoose: Boolean = false
): ViewModel