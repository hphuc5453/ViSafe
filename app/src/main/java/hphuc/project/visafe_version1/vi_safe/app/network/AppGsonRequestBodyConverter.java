package hphuc.project.visafe_version1.vi_safe.app.network;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

final class AppGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

//    private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=UTF-8");
//    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private static final MediaType MEDIA_TYPE = MediaType.get("text/plain");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    AppGsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {


        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream());
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.close();
        byte[] data = buffer.readByteArray();
        byte[] zipData = Compression.compress(data);
        Log.d("OkHttp", "Body Request: " + decodeUTF8(data));
        return RequestBody.create(MEDIA_TYPE, zipData);
    }

    private String decodeUTF8(byte[] bytes) {
        return new String(bytes, Charset.forName("UTF-8"));
    }
}
