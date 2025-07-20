# Offline RAG Android

A complete **Retrieval-Augmented Generation (RAG)** system running entirely offline on Android devices. This app demonstrates how to build intelligent question-answering capabilities without requiring internet connectivity, using on-device language models and vector similarity search.

## 🎯 Project Goals

- **Complete Offline Operation**: No internet connection required after installation
- **On-Device AI**: Uses Google AI Edge AICore for local language model inference
- **Vector Similarity Search**: Implements cosine similarity for document retrieval
- **Modern Android UI**: Built with Jetpack Compose for a clean, responsive interface
- **Educational Purpose**: Demonstrates RAG architecture patterns for mobile development

## 🏗️ Architecture

The app implements a classic RAG pipeline with three main components:

```
User Query → [Retrieval] → [Augmentation] → [Generation] → Response
```

### Core Components

1. **Knowledge Base**: In-memory document store with pre-computed embeddings
2. **Vector Retrieval**: Cosine similarity search to find relevant documents
3. **Context Augmentation**: Combines user query with retrieved documents
4. **Response Generation**: On-device LLM generates contextual answers

### Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **ML Framework**: Google AI Edge AICore
- **Minimum SDK**: Android API 34
- **Target SDK**: Android API 35

## ✨ Features

- 🔍 **Semantic Search**: Find relevant information using vector similarity
- 🤖 **On-Device LLM**: Generate responses without cloud dependencies  
- 📱 **Native Android UI**: Clean, Material Design 3 interface
- ⚡ **Real-time Responses**: Streaming text generation with loading states
- 🔒 **Privacy First**: All processing happens locally on device
- 🧠 **Extensible Knowledge**: Easy to customize with your own documents

## 📋 Prerequisites

- Android Studio Hedgehog or newer
- Android device or emulator with API level 34+
- Java 11 or higher
- Kotlin 1.9+

## 🚀 Getting Started

### Clone and Build

```bash
git clone https://github.com/nicolas-raoul/offline-rag-android.git
cd offline-rag-android
./gradlew assembleDebug
```

### Install and Run

```bash
./gradlew installDebug
```

Or import the project into Android Studio and run normally.

## 📁 Project Structure

```
app/src/main/java/io/github/nicolasraoul/android_offline_rag/
├── MainActivity.kt          # Main activity, app entry point
├── SearchScreen.kt         # Compose UI for search interface  
├── KotlinRag.kt           # RAG system implementation
├── LLM.kt                 # On-device LLM wrapper
└── ui/theme/              # Material Design theme files
```

### Key Files Explained

#### `KotlinRag.kt` - The RAG Engine
```kotlin
class KotlinRag(private val llm: LLM) {
    // In-memory knowledge base with vector embeddings
    private val knowledgeBase = mutableListOf<Document>()
    
    // Cosine similarity for document retrieval
    fun retrieve(queryVector: List<Float>, topK: Int = 2): List<Document>
    
    // Generate contextual responses using retrieved documents
    suspend fun generateAnswer(originalQuery: String, contextDocs: List<Document>): String
}
```

#### `LLM.kt` - On-Device Inference
```kotlin
class LLM(private val context: Context) {
    // Google AI Edge AICore integration
    private var model: GenerativeModel? = null
    
    // Stream responses from local model
    suspend fun generate(prompt: String): String
}
```

#### `SearchScreen.kt` - User Interface
```kotlin
@Composable
fun SearchScreen(ragSystem: KotlinRag) {
    // Compose UI with text input, search button, and results display
    // Handles loading states and error conditions
}
```

## 🛠️ Customization

### Adding Your Own Knowledge Base

Replace the sample health data in `KotlinRag.kt`:

```kotlin
knowledgeBase.addAll(listOf(
    Document(
        "Your custom knowledge text here",
        listOf(0.1f, 0.8f, 0.2f) // Your computed embeddings
    ),
    // Add more documents...
))
```

### Generating Embeddings

For production use, replace manual embeddings with a proper text embedding model:

1. Use TensorFlow Lite models for on-device embedding generation
2. Or pre-compute embeddings offline and bundle them with the app
3. Consider models like Universal Sentence Encoder or similar

### Configuring the LLM

Adjust model parameters in `LLM.kt`:

```kotlin
model = GenerativeModel(
    generationConfig {
        temperature = 0.2f      // Creativity level (0.0-1.0)
        topK = 16              // Token selection diversity  
        maxOutputTokens = 100   // Response length limit
    }
)
```

## 🔧 Development

### Building from Source

```bash
# Clean build
./gradlew clean

# Debug build
./gradlew assembleDebug

# Release build  
./gradlew assembleRelease
```

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumentation tests
./gradlew connectedAndroidTest
```

## 📱 Usage

1. Launch the app on your Android device
2. Enter a health or fitness question in the text field
3. Tap "Search" to process your query
4. View the generated response based on retrieved knowledge

### Example Queries

- "How can I improve my heart health?"
- "What should I drink daily for good health?"
- "Tell me about exercise benefits"
- "What makes a balanced diet?"

## ⚠️ Current Limitations

- **Simple Embeddings**: Uses hardcoded vectors for demonstration
- **Limited Knowledge**: Small sample dataset about health topics
- **Basic Retrieval**: Simple cosine similarity without advanced techniques
- **Model Constraints**: Depends on Google AI Edge AICore availability
- **No Persistence**: Knowledge base reloads on each app start

## 🔮 Future Enhancements

- [ ] Real text embedding model integration
- [ ] Persistent vector database storage
- [ ] Support for document ingestion at runtime
- [ ] Advanced retrieval techniques (hybrid search, re-ranking)
- [ ] Multiple knowledge domains
- [ ] Conversation history and context
- [ ] Performance optimizations

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

### Development Setup

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Google AI Edge team for on-device AI capabilities
- Android Jetpack Compose team for the modern UI framework
- The open-source community for RAG research and implementations

## 📞 Support

If you have questions or need help:

1. Check the [Issues](https://github.com/nicolas-raoul/offline-rag-android/issues) page
2. Create a new issue with detailed information
3. Join discussions in the repository

---

**Built with ❤️ for the Android and AI community**
