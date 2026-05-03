package com.oddzmint.actionpilotai.data.ai


import com.oddzmint.actionpilotai.data.model.PromptBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.oddzmint.actionpilotai.BuildConfig
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URI


class GeminiService {
    companion object {
        private const val GEMINI_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"
    }

    suspend fun getAction(userInput: String): String {
        return withContext(Dispatchers.IO) {
            val apiKey = BuildConfig.GEMINI_API_KEY
            val url = URI("$GEMINI_ENDPOINT?key=$apiKey").toURL()
            val prompt = PromptBuilder.buildPrompt(userInput)
            val requestBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                })
            }.toString()
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                doOutput = true
                outputStream.write(requestBody.toByteArray())
            }

            val responseCode = connection.responseCode

            val response = if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().readText()
            } else {
                val error = connection.errorStream?.bufferedReader()?.readText() ?: "Unknown error"
                throw Exception("API Error $responseCode: $error")
            }

            JSONObject(response)
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text")
        }
    }
}