package com.oddzmint.actionpilotai.domain

import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionType
import org.json.JSONObject

object ActionParser {
    fun parse(response: String): AIAction {
        return try {
            val cleanedResponse = response
                .replace("```json", "")
                .replace("```", "")
                .trim()

            val jsonObject = JSONObject(cleanedResponse)
            val typeString = jsonObject.optString("type", "UNKNOWN")
            val actionType = try {
                ActionType.valueOf(typeString)
            } catch (e: IllegalArgumentException) {
                ActionType.UNKNOWN
            }
            val dataObject = jsonObject.optJSONObject("data")
            val dataMap = mutableMapOf<String, String>()
            if (dataObject != null) {
                val keys = dataObject.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    dataMap[key] = dataObject.optString(key)
                }
            }

            AIAction(
                type = actionType,
                data = dataMap
            )
        } catch (e: Exception) {
            AIAction(
                type = ActionType.UNKNOWN,
                data = emptyMap()
            )
        }
    }
}