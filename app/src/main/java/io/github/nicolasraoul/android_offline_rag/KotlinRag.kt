package io.github.nicolasraoul.android_offline_rag

import kotlin.math.sqrt

// Data class to hold the search result
data class SearchResult(
    val summary: String,
    val references: List<String>
)

// A simple data class to hold our knowledge base entries.
data class Document(val text: String, val vector: List<Float>)

/**
 * A simple, self-contained RAG system that runs entirely in memory.
 * It uses a custom cosine similarity implementation for retrieval.
 */
class KotlinRag(private val llm: LLM) {

    private val knowledgeBase = mutableListOf<Document>()
    init {
        println("🧠 Initializing Simple RAG System and loading knowledge base...")
        // In a real app, this data would come from a file or a pre-built database.
        // Vectors are manually created for this example. In a real app,
        // you'd use a text embedding model to generate them.
        knowledgeBase.addAll(
            listOf(
                Document(
                    "The recommended daily water intake for adults is around 8 glasses.",
                    listOf(0.1f, 0.8f, 0.2f)
                ),
                Document(
                    "Regular exercise is crucial for cardiovascular health.",
                    listOf(0.9f, 0.1f, 0.1f)
                ),
                Document(
                    "A balanced diet should include fruits, vegetables, and proteins.",
                    listOf(0.2f, 0.2f, 0.9f)
                ),
                Document(
                    "Cardio workouts like running or swimming help strengthen the heart.",
                    listOf(0.8f, 0.2f, 0.1f) // Similar to the 'exercise' vector
                )
            )
        )
        println("✅ Knowledge base loaded with ${knowledgeBase.size} documents.\n")
    }

    /**
     * Calculates the cosine similarity between two vectors.
     * Similarity is a value between -1 and 1 (or 0 and 1 for non-negative vectors).
     * A higher value means the vectors are more similar.
     *
     * Cosine Similarity = (A · B) / (||A|| * ||B||)
     */
    private fun cosineSimilarity(vecA: List<Float>, vecB: List<Float>): Float {
        val dotProduct = vecA.zip(vecB).sumOf { (a, b) -> (a * b).toDouble() }

        val magnitudeA = sqrt(vecA.sumOf { (it * it).toDouble() })
        val magnitudeB = sqrt(vecB.sumOf { (it * it).toDouble() })

        // Avoid division by zero
        if (magnitudeA == 0.0 || magnitudeB == 0.0) return 0.0f

        return (dotProduct / (magnitudeA * magnitudeB)).toFloat()
    }

    /**
     * Retrieves the most relevant documents from the knowledge base for a given query vector.
     *
     * @param queryVector The vector representation of the user's question.
     * @param topK The number of top documents to return.
     * @return A list of the most relevant documents.
     */
    fun retrieve(queryVector: List<Float>, topK: Int = 2): List<Document> {
        println("🔎 Searching for the top $topK most relevant documents...")

        val scoredDocuments = knowledgeBase.map { doc ->
            val similarity = cosineSimilarity(queryVector, doc.vector)
            Pair(doc, similarity)
        }

        // Sort by similarity score in descending order and take the top K results.
        val topDocuments = scoredDocuments.sortedByDescending { it.second }.take(topK)

        println("🏆 Found relevant documents:")
        topDocuments.forEach { (doc, score) ->
            println("   - \"${doc.text}\" (Similarity: %.4f)".format(score))
        }

        return topDocuments.map { it.first }
    }

    /**
     * Simulates the generation step of the RAG pipeline.
     *
     * @param originalQuery The user's initial question.
     * @param contextDocs The relevant documents found by the retrieve step.
     * @return A final, generated answer.
     */
    suspend fun generateAnswer(originalQuery: String, contextDocs: List<Document>): String {
        println("\n📝 Constructing prompt with retrieved context...")

        val contextString = contextDocs.joinToString("\n- ") { it.text }

        val finalPrompt = """
        Use the following context to answer the user's question.

        Context:
        - $contextString

        User Question: $originalQuery

        Answer:
        """.trimIndent()

        println("-------------------- FINAL PROMPT SENT TO LLM --------------------")
        println(finalPrompt)
        println("------------------------------------------------------------------")

        // --- LLM GENERATION (SIMULATED) ---
        // In a real app, you would send `finalPrompt` to an on-device LLM.
        // Here, we just return a hardcoded response for demonstration.
        return llm.generate(finalPrompt)
    }
}
/*
fun main() {
    val ragSystem = KotlinRag()

    val userQuery = "How can I improve my heart health?"

    // In a real app, you'd use the same embedding model used for your
    // knowledge base to convert the userQuery into a vector.
    // We'll use a pre-defined vector that is semantically close
    // to "exercise" and "cardio".
    val queryVector = listOf(0.85f, 0.15f, 0.05f)

    println("❓ User Query: \"$userQuery\"")
    println("   (Query Vector: $queryVector)\n")

    // 1. Retrieve relevant documents
    val relevantDocs = ragSystem.retrieve(queryVector)

    // 2. Generate an answer using the query and the retrieved context
    val finalAnswer = ragSystem.generateAnswer(userQuery, relevantDocs)

    println("\n✅ Final Generated Answer:")
    println(finalAnswer)
}*/