package com.oddzmint.actionpilotai.data.model

object PromptBuilder {
    fun buildPrompt(userInput: String): String {
        return """
            You are an AI assistant inside an Android app called ActionPilotAI.
            Your job is to convert the user's natural language request into a structured JSON action.
            
            Return ONLY valid JSON.
            Do not include markdown.
            Do not include explanations.
            Do not wrap the JSON in code blocks.
            
            Available action types:
            1. CREATE_EVENT
            Required data:
            - title
            - date
            - time
            
            2. OPEN_MAPS
            Required data:
            - location
            
            3. GENERATE_REPLY
            Required data:
            - message
            
            if the request does not match any action, return:
            {
              "type":"UNKNOWN",
              "data":{}
            }
            User request:
            "$userInput
        """.trimIndent()
    }
}