package hphuc.project.visafe_version1.core.app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import hphuc.project.visafe_version1.R;

public class BitmapUtils {
    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap loadBitmapFromView(View v) {
        if (v.getMeasuredHeight() <= 0) {
            v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            Bitmap bmp = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bmp);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);

            return bmp;
        }

        Bitmap bmp = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);

        return bmp;

    }

    public static Drawable changeColorIcon(Context context, Drawable d, @ColorRes int colorId) {
        d = DrawableCompat.wrap(d);
        if (colorId != -1) {
            DrawableCompat.setTint(d, ContextCompat.getColor(context, colorId));
        }
        return d;
    }

    public static Drawable loadImageVector(Context context, @DrawableRes int resId, @ColorRes int colorId) {
        Resources res = context.getResources();
        Drawable d = VectorDrawableCompat.create(res, resId, null);
        d = DrawableCompat.wrap(d);
        if (colorId != -1) {
            DrawableCompat.setTint(d, ContextCompat.getColor(context, colorId));
        }
        return d;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void loadImageVectorResId(@NonNull ImageView view, @DrawableRes int resId) {
        loadImageVectorResId(view, resId, R.color.colorPrimary);
    }

    public static void loadImageVectorResId(@NonNull ImageView view, @DrawableRes int resId, int colorId) {
        Resources res = view.getResources();
        Drawable d = VectorDrawableCompat.create(res, resId, null);
        d = DrawableCompat.wrap(d);
        if (colorId > 0) {
            DrawableCompat.setTint(d, ContextCompat.getColor(view.getContext(), colorId));
        }
        view.setImageDrawable(d);
    }


    public static String BitmapBase64(@NonNull Bitmap bmp) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap decodeStream(Context context, Uri uri) {
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(uri);

            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of actionImage
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getPathFromURI(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            return getRealPathFromURI_BelowAPI11(context, uri);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            return getRealPathFromURI_API19(context, uri);
        else return getRealPathFromURI_API11to18(context, uri);
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }


    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static Bitmap convertBitmapRotate(File file) {
        Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
        try {
            ExifInterface ei = new ExifInterface(file.getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap rotatedBitmap;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90: {
                    rotatedBitmap = rotateBitmap(bmp, 90f);
                }
                case ExifInterface.ORIENTATION_ROTATE_180: {
                    rotatedBitmap = rotateBitmap(bmp, 180f);
                }
                case ExifInterface.ORIENTATION_ROTATE_270: {
                    rotatedBitmap = rotateBitmap(bmp, 270f);
                }
                default: {
                    rotatedBitmap = bmp;
                }
            }
            return rotatedBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
    public static Bitmap convertBitmapRotate(File file, Bitmap bmp) {
        try {
            ExifInterface ei = new ExifInterface(file.getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap rotatedBitmap;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90: {
                    rotatedBitmap = rotateBitmap(bmp, 90f);
                }
                case ExifInterface.ORIENTATION_ROTATE_180: {
                    rotatedBitmap = rotateBitmap(bmp, 180f);
                }
                case ExifInterface.ORIENTATION_ROTATE_270: {
                    rotatedBitmap = rotateBitmap(bmp, 270f);
                }
                default: {
                    rotatedBitmap = bmp;
                }
            }
            return rotatedBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

//    public static Drawable convertUriToDrawable(Context context, Uri uri){
//        Drawable returnDrawable;
//        try{
//            InputStream inputStream = context.getContentResolver().openInputStream(uri);
//            returnDrawable = Drawable.createFromStream(inputStream, uri.toString());
//        }catch (FileNotFoundException ex){
//            returnDrawable = context.getResources().getDrawable(R.drawable.no_image);
//        }
//        return returnDrawable;
//    }

    public static Bitmap resizeImage(Bitmap bmp) {
        int reduce = 1;
        if (bmp.getWidth() > 1024 || bmp.getHeight() > 1024) {
            if (bmp.getWidth() > bmp.getHeight()) {
                if ((Integer.parseInt((bmp.getWidth() + "").substring(0, 1))) > 1) {
                    reduce = Integer.parseInt((bmp.getWidth() + "").substring(0, 1));
                } else {
                    reduce = 2;
                }
            } else {
                if ((Integer.parseInt((bmp.getHeight() + "").substring(0, 1))) > 1) {
                    reduce = Integer.parseInt((bmp.getHeight() + "").substring(0, 1));
                } else {
                    reduce = 2;
                }
            }
        }
        return Bitmap.createScaledBitmap(bmp, bmp.getWidth() / reduce, bmp.getHeight() / reduce, true);
    }
}
