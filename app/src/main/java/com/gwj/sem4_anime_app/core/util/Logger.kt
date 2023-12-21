package com.gwj.sem4_anime_app.core.util

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class Logger:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val buffer = okio.Buffer()
        request.body?.writeTo(buffer)
        //Log.d("debugging_loggingrequest", "request body: ${request.body?.toString()}")
        //Log.d("debugging_loggingurl", "${request.method} request to : ${request.url}")
        val startTime = System.currentTimeMillis()
        val response = chain.proceed(request)
        val duration = System.currentTimeMillis() - startTime
        val responseString = response.peekBody(Long.MAX_VALUE)
        //Log.d("debugging_loggingresponse", responseString.string())
        //Log.d("debugging_loggingtimetook", "It took $duration milliseconds")
        return response
    }
}