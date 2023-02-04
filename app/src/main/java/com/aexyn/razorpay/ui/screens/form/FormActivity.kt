package com.aexyn.razorpay.ui.screens.form

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.aexyn.razorpay.R
import com.aexyn.razorpay.network.model.domain.UiDetail
import com.aexyn.razorpay.ui.screens.display.DisplayActivity
import com.aexyn.razorpay.util.NetworkUtil
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.composableInterop

class FormActivity : ComponentActivity() {

	private val viewModel: FormViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_composable_interop)

		//We can write checks to continously listen to the network and update reactively
		if (!NetworkUtil.isConnected(this)) {
			setContent {
				Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text("Please check Network!")
				}
			}
			return
		}

		viewModel.fetchUI()
		val recyclerView: EpoxyRecyclerView = findViewById(R.id.epoxy_recycler_view)

		recyclerView.withModels {
			val arrayOf: Array<UiDetail> = if (viewModel.uiDetail.value == null)
				arrayOf()
			else
				arrayOf(viewModel.uiDetail.value!!)

			composableInterop(id = "compose_text", keys = arrayOf) {
				FormScreen(viewModel) {
					val intent = Intent(this@FormActivity, DisplayActivity::class.java)
					intent.putExtras(viewModel.bundle)
					startActivity(intent)
				}
			}
		}
		viewModel.uiDetail.observe(this) {
			recyclerView.requestModelBuild()
		}
	}
}