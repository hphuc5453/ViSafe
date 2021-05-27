package hphuc.project.visafe_version1.vi_safe.app.util.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public interface ImageHelper {
    void loadImageFromUrl(ImageView view, Object path);

    void loadImageFromUrl(ImageView view, Object url, boolean isFitCenter);

    void loadImageFromUrl(ImageView view, Object path, int defaultResId);

    void loadImageGrayScaleFromUrl(ImageView view, Object url);

    void loadThumbnail(ImageView view, Object path, int resId);

    void loadAvatar(ImageView view, Object url);

    void loadAvatar(ImageView view, Object url,Drawable error);

    void loadBanner(ImageView view, Object url);

    void loadImageDetail(ImageView view, Object url,
                         boolean isFitCenter,
                         float sizeMultiplier,
                         OnLoadSuccessListener successListener,
                         OnLoadFailedListener failedListener);


    interface OnLoadFailedListener {
        void onFailed(Exception e);
    }

    interface OnLoadSuccessListener {
        void onSuccess(Drawable drawable);
    }
}
