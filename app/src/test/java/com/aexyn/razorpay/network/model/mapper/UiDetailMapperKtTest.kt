package com.aexyn.razorpay.network.model.mapper

import com.aexyn.razorpay.network.NetworkModule.toClassObject
import com.aexyn.razorpay.network.model.network.NetworkUiDetail
import org.junit.Test

class UiDetailMapperKtTest {
	val original =
		"{\"logo-url\":\"https://demo.ezetap.com/portal/images/logo.gif\",\"heading-text\":\"Ezetap Android Assignment\",\"uidata\":[{\"uitype\":\"label\",\"value\":\"Your Name\",\"key\":\"label_name\"},{\"uitype\":\"edittext\",\"key\":\"text_name\",\"hint\":\"Enter your name\"},{\"uitype\":\"label\",\"value\":\"Your Phone\",\"key\":\"label_phone\"},{\"uitype\":\"edittext\",\"key\":\"text_phone\",\"hint\":\"Enter your phone no\"},{\"uitype\":\"label\",\"value\":\"Your City\",\"key\":\"label_city\"},{\"uitype\":\"edittext\",\"key\":\"text_city\",\"hint\":\"Enter your city\"},{\"uitype\":\"button\",\"value\":\"Submit\"}]}"

	@Test
	fun shouldConvertToObjectWhenJsonIsCorrect() {
		val json =
			"{\"logo-url\":\"https://demo.ezetap.com/portal/images/logo.gif\",\"heading-text\":\"Ezetap Android Assignment\",\"uidata\":[{\"uitype\":\"label\",\"value\":\"Your Name\",\"key\":\"label_name\"},{\"uitype\":\"edittext\",\"key\":\"text_name\",\"hint\":\"Enter your name\"},{\"uitype\":\"label\",\"value\":\"Your Phone\",\"key\":\"label_phone\"},{\"uitype\":\"edittext\",\"key\":\"text_phone\",\"hint\":\"Enter your phone no\"},{\"uitype\":\"label\",\"value\":\"Your City\",\"key\":\"label_city\"},{\"uitype\":\"edittext\",\"key\":\"text_city\",\"hint\":\"Enter your city\"},{\"uitype\":\"button\",\"value\":\"Submit\"}]}"
		val networkUiDetail = json.toClassObject(NetworkUiDetail::class.java)
		assert(networkUiDetail != null)
	}

	@Test
	fun shouldFailToConvertToObjectWhenJsonIsWrong() {
		val json =
			"[{\"logo-url\":\"https://demo.ezetap.com/portal/images/logo.gif\",\"heading-text\":\"Ezetap Android Assignment\",\"uidata\":[{\"uitype\":\"label\",\"value\":\"Your Name\",\"key\":\"label_name\"},{\"uitype\":\"edittext\",\"key\":\"text_name\",\"hint\":\"Enter your name\"},{\"uitype\":\"label\",\"value\":\"Your Phone\",\"key\":\"label_phone\"},{\"uitype\":\"edittext\",\"key\":\"text_phone\",\"hint\":\"Enter your phone no\"},{\"uitype\":\"label\",\"value\":\"Your City\",\"key\":\"label_city\"},{\"uitype\":\"edittext\",\"key\":\"text_city\",\"hint\":\"Enter your city\"},{\"uitype\":\"button\",\"value\":\"Submit\"}]}]"
		val networkUiDetail = json.toClassObject(NetworkUiDetail::class.java)
		assert(networkUiDetail == null)
	}

	@Test
	fun shouldUiTypeShouldBeFiltered() {
		val json =
			"{\"logo-url\":\"https://demo.ezetap.com/portal/images/logo.gif\",\"heading-text\":\"Ezetap Android Assignment\"," +
					"\"uidata\":[" +
					"{\"uitype\":\"label\",\"value\":\"Your Name\",\"key\":\"label_name\"}," +
					"{\"uitype\":\"edittext\",\"key\":\"text_name\",\"hint\":\"Enter your name\"}," +
					"{\"uitype\":\"label\",\"value\":\"Your Phone\",\"key\":\"label_phone\"}," +
					"{\"uitype\":\"edittext\",\"key\":\"text_phone\",\"hint\":\"Enter your phone no\"}," +
					"{\"uitype\":\"label\",\"value\":\"Your City\",\"key\":\"label_city\"}," +
					"{\"uitype\":\"edittext\",\"key\":\"text_city\",\"hint\":\"Enter your city\"}," +
					"{\"key\":\"text_city\",\"hint\":\"Enter your city\"}," + // uiType not present
					"{\"uitype\":\"button\",\"value\":\"Submit\"}" +
					"]}"

		val networkUiDetail = json.toClassObject(NetworkUiDetail::class.java)
		assert(networkUiDetail != null)
		assert(networkUiDetail!!.uiDataList!!.size == 8)
		val uiDetail = networkUiDetail.toUiDetail()
		assert(uiDetail.uiDataList.size == 7)
	}

	@Test
	fun shouldReturnEmptyListInCaseOfNull() {
		val json =
			"{\"logo-url\":\"https://demo.ezetap.com/portal/images/logo.gif\",\"heading-text\":\"Ezetap Android Assignment\"}"

		val networkUiDetail = json.toClassObject(NetworkUiDetail::class.java)
		assert(networkUiDetail != null)
		assert(networkUiDetail!!.uiDataList == null)
		val uiDetail = networkUiDetail.toUiDetail()
		assert(uiDetail.uiDataList != null)
		assert(uiDetail.uiDataList.isEmpty())
	}

}