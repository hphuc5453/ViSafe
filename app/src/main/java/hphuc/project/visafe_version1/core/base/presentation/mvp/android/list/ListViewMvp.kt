@file:Suppress("DEPRECATION")

package hphuc.project.visafe_version1.core.base.presentation.mvp.android.list

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vivchar.rendererrecyclerviewadapter.LoadMoreViewRenderer
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import com.orhanobut.logger.Logger
import kotlinex.view.afterMeasuredSize
import hphuc.project.visafe_version1.core.base.domain.listener.OnActionNotify
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.AndroidMvpView
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.MvpActivity
import hphuc.project.visafe_version1.core.base.presentation.mvp.android.model.ViewRenderer
import hphuc.project.visafe_version1.core.base.presentation.view.ViewSizer


class ListViewMvp(
    mvpActivity: MvpActivity,
    private val recyclerView: RecyclerView,
    private val renderConfig: RenderConfig
) : AndroidMvpView(mvpActivity, ViewRootViewCreator(view = recyclerView)) {

    private val viewAdapter: RendererRecyclerActionViewAdapter = RendererRecyclerActionViewAdapter()
    private var isShowingLoadMore = false
    var itemRvClickedEvent: OnItemRvClickedListener<ViewModel>? = null
    private var rvItemClickSupport: RvItemClickSupport? = null
    val items: MutableList<ViewModel> = arrayListOf()

    override fun initCreateView() {
        recyclerView.afterMeasuredSize { size ->
            Logger.d("LayoutSizeCal $size")
            viewAdapter.viewAction = renderConfig.viewHolderSizer?.size(size)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = renderConfig.layoutManager
            renderConfig.itemAnimator?.let {
                recyclerView.itemAnimator = it
            }
            renderConfig.itemDecoration?.let {
                recyclerView.addItemDecoration(it)
            }
            recyclerView.adapter = viewAdapter
            renderConfig.loadMoreConfig?.let {
                configLoadMore(it)
            }
            rvItemClickSupport =
                RvItemClickSupport.addTo(recyclerView).setOnItemClickListener(onItemClickListener)
        }
    }

    fun enableDiffUtil() {
        viewAdapter.enableDiffUtil()
    }

    fun disableDiffUtil() {
        viewAdapter.disableDiffUtil()
    }

    fun setOnItemRvClickedListener(mOnItemRvClickedListener: OnItemRvClickedListener<ViewModel>) {
        this.itemRvClickedEvent = mOnItemRvClickedListener
    }

    private var onItemClickListener: RvItemClickSupport.OnItemClickListener =
        object : RvItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View?) {
                if (position >= 0 && position <= viewAdapter.itemCount) {
                    itemRvClickedEvent?.onItemClicked(view, position, viewAdapter.getItem(position))
                }
            }
        }

    private fun configLoadMore(loadMoreConfig: LoadMoreConfig) {
        viewAdapter.registerRenderer(loadMoreConfig.viewRenderer)
        val onReachEndItemEvent = object : OnActionNotify {
            override fun onActionNotify() {
                loadMoreConfig.loadMoreEvent.onActionNotify()
            }
        }
        recyclerView.addOnScrollListener(
            EndlessRecyclerViewScrollListener(
                recyclerView.layoutManager,
                onReachEndItemEvent,
                loadMoreConfig.isReverse
            )
        )
    }

    fun showLoadMore() {
        if (!isShowingLoadMore) {
            recyclerView.post {
                isShowingLoadMore = true
                viewAdapter.showLoadMore()
            }
        }
    }

    private fun hideLoadMore() {
        recyclerView.post {
            isShowingLoadMore = false
            viewAdapter.hideLoadMore()
        }
    }

    fun scrollToPosition(position: Int) {
        recyclerView.scrollToPosition(position)
    }

    fun smoothScrollToPosition(position: Int) {
        recyclerView.smoothScrollToPosition(position)
    }

    override fun stopMvpView() {
        rvItemClickSupport?.detach(recyclerView)
        super.stopMvpView()
    }

    fun addViewRenderer(viewRenderer: ViewRenderer<out ViewModel>) {
        viewAdapter.registerRenderer(viewRenderer.createViewBinder())
    }

    fun <T : ViewModel> setItems(items: List<T>) {
        hideLoadMore()
        this.items.clear()
        this.items.addAll(items)
        viewAdapter.setItems(this.items)
    }

    fun <T : ViewModel> addItem(item: T) {
        this.items.add(item)
        viewAdapter.setItems(this.items)
    }

    fun getItem(position: Int): ViewModel {
        return viewAdapter.getItem(position)
    }

    fun notifyDataChanged() {
        viewAdapter.notifyDataSetChanged()
    }

    fun notifyItemChanged(position: Int) {
        viewAdapter.notifyItemChanged(position)
    }

    fun notifyItemRangeInserted(positionStart: Int, itemCount: Int) {
        viewAdapter.notifyItemRangeChanged(positionStart, itemCount)
    }

    fun notifyItemRangeRemoved(positionStart: Int, itemCount: Int) {
        viewAdapter.notifyItemRangeRemoved(positionStart, itemCount)
    }

    class RenderConfig(
        val layoutManager: RecyclerView.LayoutManager,
        val itemAnimator: RecyclerView.ItemAnimator? = null,
        val viewHolderSizer: ViewSizer? = null,
        val itemDecoration: RecyclerView.ItemDecoration? = null,
        val loadMoreConfig: LoadMoreConfig? = null,
    )

    interface RenderConfigFactory {
        fun create(): RenderConfig
    }

    class EndlessRecyclerViewScrollListener(
        private val layoutManager: RecyclerView.LayoutManager?,
        private val onReachEndItemEvent: OnActionNotify,
        private val isReverse: Boolean
    ) : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            layoutManager?.let {
                val totalItem = layoutManager.itemCount
                var lastVisibleItem = 0
                if (!isReverse) {
                    if (layoutManager is LinearLayoutManager) {
                        lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    } else if (layoutManager is GridLayoutManager) {
                        lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    }

                    if (lastVisibleItem >= totalItem - 2) {
                        onReachEndItemEvent.onActionNotify()
                    }
                } else {
                    if (layoutManager is LinearLayoutManager) {
                        lastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    } else if (layoutManager is GridLayoutManager) {
                        lastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    }

                    if (lastVisibleItem <= 2 && totalItem >= 10) {
                        onReachEndItemEvent.onActionNotify()
                    }
                }
            }

        }
    }

    class LoadMoreConfig(
        val loadMoreEvent: OnActionNotify,
        val viewRenderer: LoadMoreViewRenderer,
        var isReverse: Boolean = false
    )
}