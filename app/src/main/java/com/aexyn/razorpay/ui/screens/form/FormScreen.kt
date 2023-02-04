package com.aexyn.razorpay.ui.screens.form

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aexyn.razorpay.UiType
import com.aexyn.razorpay.composables.*
import com.aexyn.razorpay.network.model.domain.UiDetail
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FormScreen(viewModel: FormViewModel, onSubmit: () -> Unit) {

	val uiDetail = viewModel.uiDetail.value
	if (uiDetail == null) {
		ShowLoader()
		return
	}

	Column(
		modifier = Modifier
			.wrapContentHeight()
			.padding(4.dp),
		verticalArrangement = Arrangement.Center
	) {
		uiDetail.headingText?.let { RazorAppBar(it) }
		uiDetail.logoByteArray?.let { byteArray ->
			GlideImage(
				contentDescription = null,
				model = null,
				modifier = Modifier.fillMaxWidth()
			) {
				it.load(byteArray)
			}
		}
		Card(
			modifier = Modifier.padding(4.dp)
		) {
			Column(
				modifier = Modifier
					.wrapContentHeight()
					.padding(18.dp),
				verticalArrangement = Arrangement.Center
			) {
				InflateItems(uiDetail, viewModel, onSubmit)
			}
		}
	}
}

@Composable
private fun InflateItems(uiDetail: UiDetail, viewModel: FormViewModel, onSubmit: () -> Unit) {
	if (uiDetail.uiDataList.isEmpty()) {
		//no data, show empty view
		return
	}

	uiDetail.uiDataList.forEach {

		when (UiType.valueOf(it.uiType)) {
			UiType.label -> RazorText(it.value)
			UiType.edittext -> {
				viewModel.addEntry(it.key, it.valueEntered)
				RazorTextField(it.hint, it.error, { value ->
					viewModel.updateEntry(it.key, value)
				})
			}

			UiType.button -> RazorButton(it.value, {
				if (viewModel.isValidated())
					onSubmit()
			})

			else -> {}
		}
	}
}