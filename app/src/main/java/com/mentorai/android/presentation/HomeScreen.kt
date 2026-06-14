package com.mentorai.android.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(modifier: Modifier) {

    val aiViewModel: GeminiAiViewModel = hiltViewModel()
    val query = remember { mutableStateOf("") }

    val responseState = aiViewModel.responseState.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val isLoading =
        responseState.value is GeminiAiViewModel.GeminiAiResponseState.Loading

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "Ask me anything")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = query.value,
            onValueChange = {
                query.value = it
            },
            label = {
                Text("Enter your query")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.value.isNotBlank()) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        aiViewModel.generateContent(query = query.value)
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.width(160.dp),
            enabled = !isLoading,
            onClick = {
                if (query.value.isNotBlank()) {
                    keyboardController?.hide()
                    aiViewModel.generateContent(query = query.value)
                }
            }) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            }
            Text(if (isLoading) "Generating.." else "Search")
        }

        Spacer(modifier = Modifier.height(16.dp))
        val displayText = when (val state = responseState.value) {

            GeminiAiViewModel.GeminiAiResponseState.Idle -> ""

            GeminiAiViewModel.GeminiAiResponseState.Loading -> ""

            is GeminiAiViewModel.GeminiAiResponseState.Success -> {
                state.result
            }

            is GeminiAiViewModel.GeminiAiResponseState.Error -> state.message
        }
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = displayText,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(Modifier)
}
