package hphuc.project.visafe_version1.core.app.view.swipe

import android.os.Bundle
import java.util.*

class ViewBinderHelper {

    private var mapStates: MutableMap<String, Int> = Collections.synchronizedMap(HashMap())
    private val mapLayouts = Collections.synchronizedMap(HashMap<String, SwipeRevealLayout>())
    private val lockedSwipeSet = Collections.synchronizedSet(HashSet<String>())

    @Volatile
    private var openOnlyOne = false
    private val stateChangeLock = Any()

    private val openCount: Int
        get() {
            var total = 0

            for (state in mapStates.values) {
                if (state == SwipeRevealLayout.STATE_OPEN || state == SwipeRevealLayout.STATE_OPENING) {
                    total++
                }
            }

            return total
        }

    /**
     * Help to save and restore open/close state of the swipeLayout. Call this method
     * when you bind your view holder with the data object.
     *
     * @param swipeLayout swipeLayout of the current view.
     * @param id a string that uniquely defines the data object of the current view.
     */
    fun bind(swipeLayout: SwipeRevealLayout, id: String) {
        if (swipeLayout.shouldRequestLayout()) {
            swipeLayout.requestLayout()
        }

        mapLayouts.values.remove(swipeLayout)
        mapLayouts.put(id, swipeLayout)

        swipeLayout.abort()
        swipeLayout.setDragStateChangeListener(object : SwipeRevealLayout.DragStateChangeListener {
            override fun onDragStateChanged(state: Int) {
                mapStates[id] = state

                if (openOnlyOne) {
                    closeOthers(id, swipeLayout)
                }
            }
        })

        // first time binding.
        if (!mapStates.containsKey(id)) {
            mapStates[id] = SwipeRevealLayout.STATE_CLOSE
            swipeLayout.close(false)
        } else {
            val state = mapStates[id]!!

            if (state == SwipeRevealLayout.STATE_CLOSE || state == SwipeRevealLayout.STATE_CLOSING ||
                    state == SwipeRevealLayout.STATE_DRAGGING) {
                swipeLayout.close(false)
            } else {
                swipeLayout.open(false)
            }
        }// not the first time, then close or open depends on the current state.

        // set lock swipe
        swipeLayout.setLockDrag(lockedSwipeSet.contains(id))
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onSaveInstanceState]
     */
    fun saveStates(outState: Bundle?) {
        if (outState == null)
            return

        val statesBundle = Bundle()
        for ((key, value) in mapStates) {
            statesBundle.putInt(key, value)
        }

        outState.putBundle(BUNDLE_MAP_KEY, statesBundle)
    }


    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onRestoreInstanceState]
     */
    fun restoreStates(inState: Bundle?) {
        if (inState == null)
            return

        if (inState.containsKey(BUNDLE_MAP_KEY)) {
            val restoredMap = HashMap<String, Int>()

            val statesBundle = inState.getBundle(BUNDLE_MAP_KEY)
            val keySet = statesBundle!!.keySet()

            if (keySet != null) {
                for (key in keySet) {
                    restoredMap[key] = statesBundle.getInt(key)
                }
            }

            mapStates = restoredMap
        }
    }

    /**
     * Lock swipe for some layouts.
     * @param id a string that uniquely defines the data object.
     */
    fun lockSwipe(vararg id: String) {
        setLockSwipe(true, *id)
    }

    /**
     * Unlock swipe for some layouts.
     * @param id a string that uniquely defines the data object.
     */
    fun unlockSwipe(vararg id: String) {
        setLockSwipe(false, *id)
    }

    /**
     * @param openOnlyOne If set to true, then only one row can be opened at a time.
     */
    fun setOpenOnlyOne(openOnlyOne: Boolean) {
        this.openOnlyOne = openOnlyOne
    }

    /**
     * Open a specific layout.
     * @param id unique id which identifies the data object which is bind to the layout.
     */
    fun openLayout(id: String) {
        synchronized(stateChangeLock) {
            mapStates[id] = SwipeRevealLayout.STATE_OPEN

            if (mapLayouts.containsKey(id)) {
                val layout = mapLayouts.get(id)
                layout!!.open(true)
            } else if (openOnlyOne) {
                closeOthers(id, mapLayouts.get(id))
            }
        }
    }

    /**
     * Close a specific layout.
     * @param id unique id which identifies the data object which is bind to the layout.
     */
    fun closeLayout(id: String) {
        synchronized(stateChangeLock) {
            mapStates[id] = SwipeRevealLayout.STATE_CLOSE

            if (mapLayouts.containsKey(id)) {
                val layout = mapLayouts.get(id)
                layout!!.close(true)
            }
        }
    }

    /**
     * Close others swipe layout.
     * @param id layout which bind with this data object id will be excluded.
     * @param swipeLayout will be excluded.
     */
    private fun closeOthers(id: String, swipeLayout: SwipeRevealLayout?) {
        synchronized(stateChangeLock) {
            // close other rows if openOnlyOne is true.
            if (openCount > 1) {
                for (entry in mapStates.entries) {
                    if (entry.key != id) {
                        entry.setValue(SwipeRevealLayout.STATE_CLOSE)
                    }
                }

                for (layout in mapLayouts.values) {
                    if (layout !== swipeLayout) {
                        layout.close(true)
                    }
                }
            }
        }
    }

    private fun setLockSwipe(lock: Boolean, vararg id: String) {
        if (id.isNullOrEmpty())
            return

        if (lock)
            lockedSwipeSet.addAll(Arrays.asList(*id))
        else
            lockedSwipeSet.removeAll(Arrays.asList(*id))

        for (s in id) {
            val layout = mapLayouts[s]
            layout?.let {
                it.setLockDrag(lock)
            }
        }
    }

    companion object {
        private val BUNDLE_MAP_KEY = "ViewBinderHelper_Bundle_Map_Key"
    }
}
