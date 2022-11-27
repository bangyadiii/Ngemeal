package com.ngemeal.ngemeal.network

import android.util.Log
import com.ngemeal.ngemeal.BuildConfig
import com.ngemeal.ngemeal.Ngemeal
import com.ngemeal.ngemeal.utils.Helpers
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient {
    private var client: Retrofit? = null
    private val apiVersion = "v1"
    private var endpoint: EndPoint? = null

    companion object {
        private  val hInstance  : HttpClient = HttpClient()

        @Synchronized
        public fun getInstance() : HttpClient {
            return hInstance
        }
    }
    init {
        buildRetrofitClient()
    }

    fun getApi() : EndPoint? {
        if(endpoint == null) {
            endpoint = client!!.create(EndPoint::class.java)
        }
        return endpoint
    }

     fun buildRetrofitClient() {
        val token = Ngemeal.getApp().getToken()
        buildRetrofitClient(token)
    }

     fun buildRetrofitClient(token : String?) {

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(2, TimeUnit.MINUTES)

        if(BuildConfig.DEBUG) {
            var intercepter = HttpLoggingInterceptor()
            intercepter.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(intercepter)
            builder.addInterceptor(ChuckInterceptor(Ngemeal.getApp()))
        }
         // content type application/json
         builder.addInterceptor(getIntercepterWithHeader("Accept", "application/json"))

        if(token != null) {
            builder.addInterceptor(getIntercepterWithHeader("Authorization", "Bearer ${token}"))
        }
        val okHttpClient = builder.build()
        client = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL + "api/${this.apiVersion}/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Helpers.getDefaultGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        endpoint = null
        Log.d("tokenn" , token.toString())
    }

    private fun getIntercepterWithHeader(headerKey: String, headerValue: String): Interceptor {
        val header = HashMap<String, String>()
        header.put(headerKey , headerValue)
        return getIntercepterWithHeader(header)
    }

    private  fun getIntercepterWithHeader(header : HashMap<String, String>) : Interceptor {
        return Interceptor{
            val original = it.request()

            val builder = original.newBuilder()
            for ((key, value ) in header) {
                builder.addHeader(key, value)
            }
            builder.method(original.method(), original.body())

            it.proceed(builder.build())
        }
    }
}