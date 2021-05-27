package hphuc.project.visafe_version1.vi_safe.app.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import hphuc.project.visafe_version1.R;


public class FontTextView extends AppCompatTextView {

    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FontTextView,
                0, 0);
        init();
    }

    private void init() {
        setTypeface(FontCache.get(getContext(), "vbd_icon.ttf"));
    }
}
