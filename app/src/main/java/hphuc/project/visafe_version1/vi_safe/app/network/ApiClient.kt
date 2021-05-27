package hphuc.project.visafe_version1.vi_safe.app.network

import com.google.gson.FieldNamingPolicy
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

class ApiClient {
    companion object {
        private fun createService(): ViSafeService {
            val retrofit: Retrofit = get()
            return retrofit.create(ViSafeService::class.java)
        }


        @JvmStatic
        private var retrofit: Retrofit = get(HttpClient.get())

        @JvmStatic
        fun get(): Retrofit {
            return retrofit
        }

        @JvmStatic
        fun get(client: OkHttpClient): Retrofit {
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
            return Retrofit.Builder().baseUrl(ConfigURL.getApiUrl())
                    .addConverterFactory(AppGsonConverterFactory.create(gson))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
        }

        @JvmStatic
        fun getClientNotJson(): ViSafeService {
            val gson = GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
            val retrofit = Retrofit.Builder().baseUrl(ConfigURL.getApiUrl())
                    .addConverterFactory(AppStringConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(HttpClient.get())
                    .build()
            return retrofit.create(ViSafeService::class.java)
        }


        @JvmStatic
        fun getClient(): ViSafeService {
            return createService()
        }

        @JvmStatic
        fun updateEndPointOfRetrofit() {
            retrofit = get(HttpClient.get())
        }
    }

}