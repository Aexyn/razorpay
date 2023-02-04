package com.aexyn.razorpay.ui.screens.form

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aexyn.razorpay.UiType
import com.aexyn.razorpay.network.NetworkModule.toClassObject
import com.aexyn.razorpay.network.RazorpayApi
import com.aexyn.razorpay.network.model.domain.UiDetail
import com.aexyn.razorpay.network.model.mapper.UiDetailMapper.toUiDetail
import com.aexyn.razorpay.network.model.network.NetworkUiDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormViewModel : ViewModel() {
	private val TAG = "FormViewModel"

	private val _uiDetail = MutableLiveData<UiDetail>()
	val uiDetail = _uiDetail

	val bundle = Bundle()

	fun fetchUI() {
		viewModelScope.launch(Dispatchers.IO) {
			val jsonObject =
				RazorpayApi.fetchCustomUI("https://demo.ezetap.com/mobileapps/android_assignment.json")

			try {
				val detail =
					jsonObject.toString().toClassObject(NetworkUiDetail::class.java)?.toUiDetail()
				_uiDetail.postValue(detail)
				launch {
					detail?.logoUrl?.let {
						val bytes = RazorpayApi.fetchLogo(it)
						_uiDetail.postValue(detail.copy(logoByteArray = bytes))
					}
				}

			} catch (e: Exception) {
				Log.d(TAG, "initApi: $e")
			}

		}
	}

	fun isValidated(): Boolean {
		val detail = uiDetail.value ?: return false
		var isValidated = true

		val uiDataList: List<UiDetail.UiData> = detail.uiDataList.map {
			if (UiType.valueOf(it.uiType) != UiType.edittext)
				return@map it

			val valueEntered = bundle.getString(it.key)
			val error = if (valueEntered.isNullOrBlank()) {
				isValidated = false
				"Cannot be empty!!"
			} else {
				null
			}
			it.copy(error = error, valueEntered = valueEntered)
		}
		_uiDetail.postValue(detail.copy(uiDataList = uiDataList))

		return isValidated
	}

	fun updateEntry(key: String?, value: String) {
		bundle.putString(key, value)
	}

	fun addEntry(key: String?, valueEntered: String?) {
		bundle.putString(key, valueEntered)
	}
}