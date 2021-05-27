package hphuc.project.visafe_version1.core.app.util;


import androidx.annotation.NonNull;

public class DoubleTouchPreventProvider {
    private static DoubleTouchPrevent doubleTouchPrevent = new DoubleTouchPrevent();

    @NonNull
    public static DoubleTouchPrevent createDoubleTouchPrevent() {
        return doubleTouchPrevent;
    }
}
