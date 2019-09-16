//package com.app.torch.utils.interceptors
//
//import com.app.torch.utils.enums.LanguageCode
//import com.app.torch.utils.enums.UserSavedDataKeys
//import com.app.torch.utils.managers.SharedPreferencesManagerInterface
//import okhttp3.Interceptor
//import okhttp3.Response
//import java.io.IOException
//import java.util.*
//
//class HeadersInterceptor(private val sharedPreferencesManager: SharedPreferencesManagerInterface) :
//    Interceptor {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request().newBuilder()
//        var language = LanguageCode.EN.code
//
//        if (Locale.getDefault().language == LanguageCode.AR.code) language = LanguageCode.AR.code
//
//        request.addHeader("lang", language)
//            .addHeader("os", "android")
//        if (sharedPreferencesManager.get(UserSavedDataKeys.TOKEN.key, "").isNotEmpty()) {
//            request.addHeader(
//                "Authorization",
//                "Bearer ${sharedPreferencesManager.get(UserSavedDataKeys.TOKEN.key, "")}"
//            )
//        }
//
//        return chain.proceed(request.build())
//    }
//}