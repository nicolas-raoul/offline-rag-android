package io.github.nicolasraoul.android_offline_rag

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    ragSystem: KotlinRag // Accept KotlinRag instance
) {
    var query by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("Ask a question about health or fitness...") }
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    // This is a placeholder for getting query vector.
    // In a real app, you'd use a text embedding model.
    val dummyQueryVector = listOf(0.5f, 0.3f, 0.7f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Offline RAG Search",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Your question") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (query.isNotBlank() && !isLoading) {
                    isLoading = true
                    resultText = "Searching..." // Update UI
                    coroutineScope.launch {
                        try {
                            // 1. Retrieve relevant documents using KotlinRag
                            //    (Using dummyQueryVector for now)
                            val relevantDocs = ragSystem.retrieve(dummyQueryVector, topK = 2)

                            // 2. Generate an answer using KotlinRag
                            val answer = ragSystem.generateAnswer(query, relevantDocs)
                            resultText = answer
                        } catch (e: Exception) {
                            resultText = "Error: ${e.localizedMessage}"
                        } finally {
                            isLoading = false
                        }
                    }
                }
            },
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(if (isLoading) "Searching..." else "Search")
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Answer:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = resultText,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}