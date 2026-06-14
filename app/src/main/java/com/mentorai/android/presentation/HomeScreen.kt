package com.mentorai.android.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    modifier: Modifier
) {

    val aiViewModel: GeminiAiViewModel = hiltViewModel()
    val query = remember { mutableStateOf("") }

    val responseState = aiViewModel.responseState.collectAsStateWithLifecycle()

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
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (query.value.isNotBlank()) {
                    aiViewModel.generateContent(query = query.value)
                } else {
                    println("Query is empty")
                }
            }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))
        val displayText = when (val state = responseState.value) {

            GeminiAiViewModel.GeminiAiResponseState.Idle -> ""

            GeminiAiViewModel.GeminiAiResponseState.Loading -> {
                CircularProgressIndicator()
                "Generating response .."
            }

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
