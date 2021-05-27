package hphuc.project.visafe_version1.core.app.view.loading;


import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public final class Loadinger {

    private WeakReference<Window> windowWeakReference;

    private Loading loading;

    /**
     * Constructor
     */
    private Loadinger() {
        //Utility classes should not be instantiated
    }

    /**
     * Creates the Alert, and maintains a reference to the calling Activity
     *
     * @param window The calling Activity
     * @return This Alerter
     */
    public static Loadinger create(@NonNull Activity activity, @NonNull final Window window) {
        Loadinger loadinger = new Loadinger();
        Loading loading = getCurrentLoadingInWindow(window);
        if (loading == null) {
            loading = new Loading(activity);
        }
        loadinger.setWindow(window);
        loadinger.setLoading(loading);
        return loadinger;
    }

    @Nullable
    private static Loading getCurrentLoadingInWindow(Window window) {
        Loading currentLoading = null;
        try {
            final ViewGroup decorView = (ViewGroup) window.getDecorView();

            //Find all Alert Views in Parent layout
            for (int i = 0; i < decorView.getChildCount(); i++) {
                final Loading childView = decorView.getChildAt(i) instanceof Loading ? (Loading) decorView.getChildAt(i) : null;
                if (childView != null) {
                    currentLoading = childView;
                    break;
                }
            }

        } catch (Exception ex) {
            Log.e(Loadinger.class.getClass().getSimpleName(), Log.getStackTraceString(ex));
        }
        return currentLoading;
    }

    /**
     * Cleans up the currently showing loading view, if one is present
     *
     * @param window The current Window
     */
    private static void hideCurrentLoading(@NonNull final Window window) {
        try {
            final ViewGroup decorView = (ViewGroup) window.getDecorView();

            //Find all Alert Views in Parent layout
            for (int i = 0; i < decorView.getChildCount(); i++) {
                final Loading childView = decorView.getChildAt(i) instanceof Loading ? (Loading) decorView.getChildAt(i) : null;
                if (childView != null) {
                    childView.hide();
                }
            }

        } catch (Exception ex) {
            Log.e(Loadinger.class.getClass().getSimpleName(), Log.getStackTraceString(ex));
        }
    }

    /**
     * Hides the currently showing loading view, if one is present
     */
    public void hide() {
        if (windowWeakReference != null && windowWeakReference.get() != null) {
            hideCurrentLoading(windowWeakReference.get());
        }
    }

    /**
     * Shows the Alert, after it's built
     *
     * @return An Alert object check can be altered or hidden
     */
    public Loading show() {
        //This will get the Activity Window's DecorView
        if (getWindowWeakReference() != null) {
            new Handler(Looper.getMainLooper()).post(() -> {
                //Add the new Alert to the View Hierarchy
                final ViewGroup decorView = getActivityDecorView();
                Loading loadingNeedAdd = getLoading();
                if (decorView != null) {
                    if (loadingNeedAdd.getParent() == null) {
                        decorView.addView(loadingNeedAdd);
                    }
                    loadingNeedAdd.show();
                }
            });
        }

        return getLoading();
    }

    /**
     * Set the onClickListener for the Alert
     *
     * @param onClickListener The onClickListener for the Alert
     * @return This Alerter
     */
    public Loadinger setOnClickListener(@NonNull final View.OnClickListener onClickListener) {
        if (getLoading() != null) {
            getLoading().setOnClickListener(onClickListener);
        }

        return this;
    }

    /**
     * Gets the Alert associated with the Alerter
     *
     * @return The current Alert
     */
    private Loading getLoading() {
        return loading;
    }

    /**
     * Sets the Alert
     *
     * @param loading The Alert to be references and maintained
     */
    private void setLoading(final Loading loading) {
        this.loading = loading;
    }

    @Nullable
    private WeakReference<Window> getWindowWeakReference() {
        return windowWeakReference;
    }

    /**
     * Get the enclosing Decor View
     *
     * @return The Decor View of the Activity the Alerter was called from
     */
    @Nullable
    private ViewGroup getActivityDecorView() {
        ViewGroup decorView = null;

        if (getWindowWeakReference() != null && getWindowWeakReference().get() != null) {
            decorView = (ViewGroup) getWindowWeakReference().get().getDecorView();
        }

        return decorView;
    }

    /**
     * Creates a weak reference to the calling Activity
     *
     * @param window The calling Window
     */
    private void setWindow(@NonNull final Window window) {
        windowWeakReference = new WeakReference<>(window);
    }
}