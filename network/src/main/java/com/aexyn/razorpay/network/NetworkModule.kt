package com.aexyn.razorpay.network

import com.aexyn.razorpay.network.util.NetworkUtil.attachNetworkInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import java.io.IOException
import java.util.concurrent.TimeUnit

object NetworkModule {

	val okHttpClient by lazy {
		val builder = OkHttpClient.Builder().apply {
			connectTimeout(1, TimeUnit.MINUTES)
			readTimeout(1, TimeUnit.MINUTES)
			writeTimeout(1, TimeUnit.MINUTES)
		}

		attachNetworkInterceptor(builder)

		builder.build()
	}

	private val moshi by lazy {
		Moshi.Builder()
			.add(KotlinJsonAdapterFactory())
			.build()!!
	}

	@Throws(IOException::class)
	fun <T> String.toClassObject(clazz: Class<T>): T? {
		return try {
			moshi.adapter(clazz)?.fromJson(this)
		} catch (e: Exception) {
			null
		}
	}

	fun <T> T.toJson(clazz: Class<T>) = moshi.adapter(clazz)?.toJson(this)

}