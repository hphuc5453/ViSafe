package hphuc.project.visafe_version1.core.base.presentation.mvp.android.list

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hphuc.project.visafe_version1.core.base.presentation.view.ViewSizer

class GridRenderConfigFactory(val input: Input) : ListViewMvp.RenderConfigFactory {
    override fun create(): ListViewMvp.RenderConfig {
        val spanCount: Int = input.spanCount
        val orientation: Int = getOrientation(input.orientation)
        val layoutManager =
            GridLayoutManager(input.context, spanCount, orientation, input.reverseLayout)
        layoutManager.spanSizeLookup = input.spanSizeLookup
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
            Orientation.HORIZONTAL -> GridLayoutManager.HORIZONTAL
            Orientation.VERTICAL -> GridLayoutManager.VERTICAL
        }

    class Input(
        val context: Context,
        val orientation: Orientation = Orientation.VERTICAL,
        val reverseLayout: Boolean = false,
        val spanCount: Int,
        val spanSizeLookup: GridLayoutManager.SpanSizeLookup = GridLayoutManager.DefaultSpanSizeLookup(),
        val decoration: RecyclerView.ItemDecoration? = null,
        val loadMoreConfig: ListViewMvp.LoadMoreConfig? = null,
        val animator: RecyclerView.ItemAnimator = DefaultItemAnimator(),
        val viewHolderSizer: ViewSizer? = null
    )

    enum class Orientation {
        HORIZONTAL, VERTICAL
    }
}