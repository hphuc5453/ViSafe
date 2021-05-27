package hphuc.project.visafe_version1.vi_safe.app.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class FontCache {
    public static final String VBD_CATEGORY = "vbd_category.ttf";
    private static Hashtable<String, Typeface> fontCache = new Hashtable<>();

    public static Typeface get(Context context, String name) {
        Typeface typeface = fontCache.get(name);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), name);
            } catch (Exception e) {
                return null;
            }
            fontCache.put(name, typeface);
        }
        return typeface;
    }
}
