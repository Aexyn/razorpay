package com.aexyn.razorpay.network.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkUiDetail(
	@Json(name = "heading-text")
	val headingText: String?,
	@Json(name = "logo-url")
	val logoUrl: String?,
	@Json(name = "uidata")
	val uiDataList: List<UiData?>?
) {
	@JsonClass(generateAdapter = true)
	data class UiData(
		val hint: String?,
		val key: String?,
		@Json(name = "uitype")
		val uiType: String?,
		val value: String?
	)
}