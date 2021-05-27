package hphuc.project.visafe_version1.core.base.presentation.mvp.android.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RvItemClickSupport(recyclerView: RecyclerView) {
    private var mRecyclerView: RecyclerView? = recyclerView
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnClickListener: View.OnClickListener = View.OnClickListener { v ->
        mRecyclerView?.let {
            if (mOnItemClickListener != null) {
                val holder = it.getChildViewHolder(v)
                val rv = it
                holder?.let {
                    val position = it.adapterPosition
                    mOnItemClickListener?.onItemClicked(rv, position, v)
                }
            }
        }
    }

    private var mAttachListener: RecyclerView.OnChildAttachStateChangeListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewDetachedFromWindow(view: View) {
        }

        override fun onChildViewAttachedToWindow(view: View) {
            view.let {
                if (mOnItemClickListener != null) {
                    if (!it.hasOnClickListeners()) {
                        it.setOnClickListener(mOnClickListener)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun addTo(rv: RecyclerView): RvItemClickSupport {
            return RvItemClickSupport(rv)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener): RvItemClickSupport {
        this.mOnItemClickListener = listener
        return this
    }

    fun detach(rv: RecyclerView) {
        rv.removeOnChildAttachStateChangeListener(mAttachListener)
    }

    interface OnItemClickListener {
        fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View?)
    }

    init {
        this.mRecyclerView?.addOnChildAttachStateChangeListener(mAttachListener)
    }
}