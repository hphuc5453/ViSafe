package hphuc.project.visafe_version1.vi_safe.app.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import hphuc.project.visafe_version1.vi_safe.app.network.socket.SocketUtils
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


class HttpClient {

    companion object {
        private const val CONNECT_TIME_OUT: Long = 60
        private const val READ_TIME_OUT: Long = 60

        @JvmStatic
        private fun getBaseBuilder(): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .retryOnConnectionFailure(true)
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .connectionPool(ConnectionPool(10, 5, TimeUnit.MINUTES))
            try {
                val trustManagerFactory = TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm()
                )
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers = trustManagerFactory.trustManagers
                if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                    throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
                }
                val trustManager = trustManagers[0] as X509TrustManager
                builder.sslSocketFactory(SocketUtils.getSSLSocketFactory()!!, trustManager)
                builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
            } catch (e: Exception) {
            }

            return SocketUtils.enableTls12OnPreLollipop(builder)
        }

        @JvmStatic
        private fun providesLogInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        @JvmStatic
        private fun provideOkClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return getBaseBuilder()
                    .addInterceptor(HeaderInterceptor())
                    .addInterceptor(loggingInterceptor)
//                    .addInterceptor(LoggingInterceptor(LoggingInterceptor.LoggingProcessorFactory { SQLiteLoggingProcessor() }))
                    .build()
        }

        @JvmStatic
        private val okClient: OkHttpClient by lazy {
            provideOkClient(providesLogInterceptor())
        }

        @JvmStatic
        fun get(): OkHttpClient {
            return okClient
        }
    }

}