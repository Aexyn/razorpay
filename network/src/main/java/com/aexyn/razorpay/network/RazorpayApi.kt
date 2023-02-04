package com.aexyn.razorpay.network

import okhttp3.Request
import org.json.JSONObject

object RazorpayApi {
	fun fetchCustomUI(url: String): JSONObject {
		val request = Request.Builder()
			.url(url)
			.build()

		val newCall = NetworkModule.okHttpClient.newCall(request)

		newCall.execute().use { response ->
			val string = response.body?.string()!!
			return JSONObject(string)
		}
	}

	fun fetchLogo(url: String): ByteArray? {
		val request = Request.Builder()
			.url(url)
			.build()

		val newCall = NetworkModule.okHttpClient.newCall(request)

		val response1 = newCall.execute()
		response1.use { response ->
			val imageBytes = response.body?.bytes()
			return imageBytes
		}
	}
}