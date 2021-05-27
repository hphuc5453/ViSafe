package hphuc.project.visafe_version1.core.base.presentation.mvp.android.model

import android.view.View
import hphuc.project.visafe_version1.core.base.presentation.view.ViewAction
import hphuc.project.visafe_version1.core.base.presentation.view.ViewSize
import hphuc.project.visafe_version1.core.base.presentation.view.ViewSizer

class WidthHeightViewSizer(val numRow: Int, val padding: Int = 0) : ViewSizer {
    override fun size(viewParentSize: ViewSize): ViewAction {
        return object : ViewAction {
            override fun action(view: View) {
                val size = (viewParentSize.width / numRow) - (padding * 2)
                view.layoutParams.width = size
                view.layoutParams.height = size
            }
        }
    }
}