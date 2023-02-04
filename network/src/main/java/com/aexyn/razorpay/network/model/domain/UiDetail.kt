package com.aexyn.razorpay.network.model.domain

data class UiDetail(
	val headingText: String?,
	val logoUrl: String?,
	val logoByteArray: ByteArray? = null,
	val uiDataList: List<UiData>
) {
	data class UiData(
		val uiType: String,
		val hint: String?,
		val key: String?,
		val value: String?,
		val valueEntered: String? = null,
		val error: String? = null
	)
}