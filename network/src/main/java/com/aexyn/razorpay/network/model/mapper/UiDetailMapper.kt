package com.aexyn.razorpay.network.model.mapper

import com.aexyn.razorpay.network.model.domain.UiDetail
import com.aexyn.razorpay.network.model.network.NetworkUiDetail

object UiDetailMapper {
	fun NetworkUiDetail.toUiDetail() = UiDetail(
		headingText = this.headingText,
		logoUrl = this.logoUrl,
		uiDataList = if (this.uiDataList == null)
			emptyList()
		else {
			this.uiDataList.filter {
				it?.uiType != null
			}.map {
				UiDetail.UiData(
					hint = it!!.hint,
					key = it.key,
					uiType = it.uiType!!,
					value = it.value,
				)
			}
		}
	)
}