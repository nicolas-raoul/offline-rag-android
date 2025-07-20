package io.github.nicolasraoul.android_offline_rag

import io.github.nicolasraoul.android_offline_rag.SearchScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.nicolasraoul.android_offline_rag.ui.theme.AndroidOfflineRAGTheme

class MainActivity : ComponentActivity() {

    private lateinit var llmInstance: LLM
    private lateinit var kotlinRagInstance: KotlinRag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Instantiate LLM
        llmInstance = LLM(applicationContext)

        // 2. Instantiate KotlinRag with the LLM instance
        kotlinRagInstance = KotlinRag(llmInstance)

        enableEdgeToEdge()
        setContent {
            AndroidOfflineRAGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SearchScreen( // Replace Greeting with SearchScreen
                        modifier = Modifier.padding(innerPadding),
                        ragSystem = kotlinRagInstance
                    )
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() { // Rename to reflect the new screen
    AndroidOfflineRAGTheme {
        SearchScreen()
    }
}*/