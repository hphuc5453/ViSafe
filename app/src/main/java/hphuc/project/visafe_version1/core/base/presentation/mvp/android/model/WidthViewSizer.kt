package hphuc.project.visafe_version1.core.base.presentation.mvp.android.model

import android.view.View
import hphuc.project.visafe_version1.core.base.presentation.view.ViewAction
import hphuc.project.visafe_version1.core.base.presentation.view.ViewSize
import hphuc.project.visafe_version1.core.base.presentation.view.ViewSizer

class WidthViewSizer(val numCol: Int, val padding: Int = 0) : ViewSizer {
    override fun size(viewParentSize: ViewSize): ViewAction {
        return object : ViewAction {
            override fun action(view: View) {
                view.layoutParams.width = (viewParentSize.width / numCol) - (padding * 2)
            }
        }
    }
}