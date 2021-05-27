package hphuc.project.visafe_version1.vi_safe.app.util;

import android.content.Context;
import android.util.Log;

import androidx.collection.SparseArrayCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import hphuc.project.visafe_version1.R;


public class TrimSign {

    public static final String TAG = TrimSign.class.getName();

    private static TrimSign instance = null;

    public static synchronized TrimSign getInstances(Context context) {
        if (instance == null) {
            instance = new TrimSign(context);
        }
        return instance;
    }

    private SparseArrayCompat<String> mTrimSign = new SparseArrayCompat<>();

    private TrimSign(Context context) {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.trimsign);

            String dataStr = convertStreamToString(is);

            JSONArray jsArray = new JSONArray(dataStr);
            int size = jsArray.length();
            for (int i = 0; i < size; i++) {
                JSONObject jsObj = jsArray.getJSONObject(i);
                String key = jsObj.getString("Key");
                String value = jsObj.getString("Value");

                int k = Integer.decode(key);
                mTrimSign.put(k, value);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public String unicodeTrimSign(final String str) {
        int length = str.length();
        String out = "";
        for (int i = 0; i < length; i++) {

            char cTemp = str.charAt(i);
            String cUniHex = String.format("0x%s", Integer.toHexString(cTemp));

            int key = Integer.decode(cUniHex);
            String c = mTrimSign.get(key);
            if (c != null) {
                out += String.valueOf(c);
            } else {
                out += String.valueOf(cTemp);
            }
        }
        return out;
    }


}
