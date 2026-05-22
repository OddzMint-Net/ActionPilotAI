package com.oddzmint.actionpilotai

import com.oddzmint.actionpilotai.domain.action.ActionType
import com.oddzmint.actionpilotai.domain.ActionParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ActionParserTest {
    @Test
    fun `parserAction return create even action when response contains valid create event json`() {
        //Arrange
        val json = """
            {
            "type": "CREATE_EVENT",
            "data": {
            "title": "Team meeting",
            "date": "2026-05-10",
            "time": "08:30"
            }
         }
        """.trimIndent()
        // Act
        val result = ActionParser.parse(json)

        // Assert
        assertEquals(ActionType.CREATE_EVENT, result.type)
        assertEquals("Team meeting", result.data["title"])
        assertEquals("2026-05-10", result.data["date"])
        assertEquals("08:30", result.data["time"])
    }

    @Test
    fun `parse returns unknown action when json is invalid`() {
        //Arrange
        val invalidJson = "This is not valid JSON"

        //Act
        val result = ActionParser.parse(invalidJson)
        //Assert
        assertEquals(ActionType.UNKNOWN, result.type)
        assertTrue(result.data.isEmpty())
    }

    @Test
    fun `parse returns unknown action when type is invalid`() {
        val json = """
            {
            "type":"FLY_TO_MOON",
            "data":{
            "destination":"Mars"
            }   
         }
        """.trimIndent()
        val result = ActionParser.parse(json)
        assertEquals(ActionType.UNKNOWN, result.type)
    }

    @Test
    fun `parse returns empty data when data object is missing`() {
        val json = """
            {
            "type": "CREATE_EVENT"
            }
        """.trimIndent()
        val result = ActionParser.parse(json)
        assertEquals(ActionType.CREATE_EVENT, result.type)
        assertTrue(result.data.isEmpty())
    }

    @Test
    fun `parse returns open maps action when response contains valid open maps json`() {
        val json = """
            {
            "type":"OPEN_MAPS",
            "data":{
            "location":"Cape Town"
             }
            }
        """.trimIndent()
        val result = ActionParser.parse(json)
        assertEquals(ActionType.OPEN_MAPS, result.type)
        assertEquals("Cape Town", result.data["location"])
    }


    @Test
    fun `parse returns generate reply action when response contains valid generate reply json`() {
        val json = """
            {
            "type": "GENERATE_REPLY",
            "data":{
            "message": "Thank you for reaching out. I will get back to you soon."
              }
            }
        """.trimIndent()

        val result = ActionParser.parse(json)
        assertEquals(ActionType.GENERATE_REPLY, result.type)
        assertEquals("Thank you for reaching out. I will get back to you soon.", result.data["message"])
    }

    @Test
    fun `parse return opens url action when response contains valid open url json`() {
        val json = """
            {
            "type": "OPEN_URL",
            "data":{
            "url":"https://example.com"
            }
           }
        """.trimIndent()
        val result = ActionParser.parse(json)
        assertEquals(ActionType.OPEN_URL, result.type)
        assertEquals("https://example.com", result.data["url"])
    }

    @Test
    fun `parse returns search web action when response contains valid search web json`() {
        val json = """
            {
            "type":"SEARCH_WEB",
            "data": {
            "query": "latest Android development trends"
            }
            }
        """.trimIndent()
        val result = ActionParser.parse(json)
        assertEquals(ActionType.SEARCH_WEB, result.type)
        assertEquals("latest Android development trends", result.data["query"])
    }

    @Test
    fun `parse returns share text action when response contains valid share text json`() {
        val json = """
            {
            "type":"SHARE_TEXT",
            "data":{
            "text":"ActionPilotAI is working" 
            }
           }
            
        """.trimIndent()
        val result = ActionParser.parse(json)
        assertEquals(ActionType.SHARE_TEXT, result.type)
        assertEquals("ActionPilotAI is working", result.data["text"])
    }

    @Test
    fun `parse returns dial phone action when response contains valid phone json`() {
        val json = """
            {
            "type":"DIAL_PHONE",
            "data":{
            "phone": "0123456789"
            }
           }
        """.trimIndent()

        val result = ActionParser.parse(json)
        assertEquals(ActionType.DIAL_PHONE,result.type)
        assertEquals("0123456789",result.data["phone"])
    }
}