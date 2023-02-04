package com.aexyn.razorpay.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RazorAppBar(text: String) {
	TopAppBar(title = { Text(text = text) }
	)
}

@Composable
fun RazorButton(text: String?, onClick: () -> Unit, modifier: Modifier = Modifier) {
	if (text == null) return

	Button(modifier = modifier.fillMaxWidth(), content = { Text(text) }, onClick = onClick)
}

@Composable
fun RazorText(text: String?, modifier: Modifier = Modifier) {
	if (text == null) return

	Text(text = text, modifier = modifier.wrapContentWidth())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RazorTextField(
	hint: String?,
	errorText: String?,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier
) {
	var text by remember { mutableStateOf("") }
	var isError by remember { mutableStateOf(false) }
	isError = errorText.isNullOrBlank().not()

	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(bottom = 24.dp)
	) {
		OutlinedTextField(
			value = text,
			singleLine = true,
			onValueChange = {
				text = it.take(20)
				onValueChange(text)
				isError = false
			},
			trailingIcon = {
				if (isError)
					Icon(Icons.Filled.Info, errorText, tint = MaterialTheme.colorScheme.error)
			},
			isError = isError,
			label = { hint?.let { Text(it) } },
		)
		if (isError) {
			Text(
				text = errorText!!,
				style = MaterialTheme.typography.bodySmall,
				color = MaterialTheme.colorScheme.error,
				modifier = Modifier.padding(start = 8.dp)
			)
		}
	}
}

@Composable
fun ShowLoader() {
	Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		CircularProgressIndicator()
	}
}

