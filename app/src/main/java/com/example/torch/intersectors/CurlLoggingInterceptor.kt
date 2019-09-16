package com.app.torch.utils.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.Charset

class CurlLoggingInterceptor : Interceptor {

    companion object {
        private val UTF8 = Charset.forName("UTF-8")
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        var curlCmd = " \n -X ${request.method()}\n"

        val headers = request.headers()
        for (i in 0 until headers.size()) {
            val name = headers.name(i)
            var value = headers.value(i)

            val start = 0
            val end = value.length - 1
            if (value[start] == '"' && value[end] == '"') {
                value = "\\\"" + value.substring(1, end) + "\\\""
            }

            curlCmd += " -H \"$name: $value\"\n"
        }

        request.body()?.let {
            val buffer = Buffer().apply { it.writeTo(this) }
            val charset = it.contentType()?.charset(UTF8) ?: UTF8
            val data = buffer.readString(charset).replace("\n", "\\n")
            curlCmd += if (!data.contains("multipart/form-data")) {
                " --data $'$data'\n"
            } else {
                " --data $'multipart/form-data'\n"
            }
        }

        Log.d("interceptor", " \n ${request.url()}${URLDecoder.decode(curlCmd, "UTF-8")} ")

        return chain.proceed(request)
    }
}