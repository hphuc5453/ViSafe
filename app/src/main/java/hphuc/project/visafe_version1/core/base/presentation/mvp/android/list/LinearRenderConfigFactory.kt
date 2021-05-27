@file:Suppress("DEPRECATION")

package hphuc.project.visafe_version1.core.base.presentation.mvp.android.list

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hphuc.project.visafe_version1.core.base.presentation.view.ViewSizer

class LinearRenderConfigFactory(val input: Input) : ListViewMvp.RenderConfigFactory {
    override fun create(): ListViewMvp.RenderConfig {
        val orientation: Int = getOrientation(input.orientation)
        val layoutManager = LinearLayoutManager(input.context, orientation, input.reverse)
        layoutManager.isAutoMeasureEnabled = false
        return ListViewMvp.RenderConfig(
            layoutManager,
            input.animator,
            input.viewHolderSizer,
            input.decoration,
            input.loadMoreConfig
        )
    }

    private fun getOrientation(orientation: Orientation): Int =
        when (orientation) {
            Orientation.HORIZONTAL -> LinearLayoutManager.HORIZONTAL
            Orientation.VERTICAL -> LinearLayoutManager.VERTICAL
        }

    class Input(
        val context: Context,
        val orientation: Orientation,
        val reverse: Boolean = false,
        val decoration: RecyclerView.ItemDecoration? = null,
        val loadMoreConfig: ListViewMvp.LoadMoreConfig? = null,
        val animator: RecyclerView.ItemAnimator = DefaultItemAnimator(),
        val viewHolderSizer: ViewSizer? = null
    )

    enum class Orientation {
        HORIZONTAL, VERTICAL
    }
}