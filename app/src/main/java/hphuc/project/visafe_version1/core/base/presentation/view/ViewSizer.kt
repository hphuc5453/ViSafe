package hphuc.project.visafe_version1.core.base.presentation.view

import hphuc.project.visafe_version1.core.base.presentation.view.ViewAction
import hphuc.project.visafe_version1.core.base.presentation.view.ViewSize

interface ViewSizer {
    fun size(viewParentSize: ViewSize): ViewAction
}