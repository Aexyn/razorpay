package com.aexyn.razorpay.network.util

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object NetworkUtil {

	fun attachNetworkInterceptor(builder: OkHttpClient.Builder) {

		val interceptor = HttpLoggingInterceptor { message -> Log.d("Interceptor", message) }
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
		builder.addNetworkInterceptor(interceptor)
	}
}