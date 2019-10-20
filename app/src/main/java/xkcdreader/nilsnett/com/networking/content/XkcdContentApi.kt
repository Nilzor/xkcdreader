package xkcdreader.nilsnett.com.networking.content

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

/**
 * Retrofit API interface for the main content API for XKCD comics
 */
interface XkcdContentApi {
    companion object {
        val BASE_URL = "http://xkcd.com"

        fun createInstance(): XkcdContentApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(XkcdContentApi::class.java)
        }
    }


    @GET("/{id}/info.0.json")
    fun getXkcd(@Path("id") id: Int): Call<XkcdContentNetworkData>
}