package com.aexyn.razorpay.ui.screens.display

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aexyn.razorpay.composables.RazorText

class DisplayActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Column {
				intent.extras?.keySet()?.forEach {
					val text = intent.extras?.getString(it)
					Row(Modifier.padding(8.dp)) {
						RazorText("$it:\t", Modifier.padding(end = 8.dp))
						RazorText(text, Modifier.padding(end = 8.dp))
					}
				}
			}
		}
	}
}