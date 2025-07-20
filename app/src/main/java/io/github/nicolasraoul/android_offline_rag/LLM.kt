// In your LLM.kt file
package io.github.nicolasraoul.android_offline_rag

import android.content.Context
import com.google.ai.edge.aicore.GenerativeModel
import com.google.ai.edge.aicore.generationConfig
import kotlinx.coroutines.flow.collect // Ensure this import is present

class LLM(private val context: Context) {

    private var model: GenerativeModel? = null

    init { // Initialize the model in the init block or lazily
        model = GenerativeModel(
            generationConfig {
                this.context = this@LLM.context // Correct way to reference outer class context
                temperature = 0.2f
                topK = 16
                maxOutputTokens = 20
            }
        )
    }

    suspend fun generate(prompt: String): String { // Make it suspend as generateContentStream is likely suspend
        var responseText = ""
        android.util.Log.d("AICoreSetup", "Starting generateContentStream for prompt: $prompt")
        try {
            model?.generateContentStream(prompt)
                ?.collect { response ->
                    responseText += response.text
                    android.util.Log.d("Offline AI chat AICoreSetup", "Received chunk: ${response.text}")
                }
        } catch (e: Exception) {
            android.util.Log.e("AICoreSetup", "Error generating content: ${e.message}", e)
            // Handle error appropriately, maybe return an error message or throw
            return "Error generating content: ${e.localizedMessage}"
        }

        android.util.Log.d("Offline AI chat AICoreSetup", "Final response text: $responseText")
        return responseText
    }
}