package org.lnu.timetable.integration.tests.util

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object JsonUtil {
  val objectMapper = new ObjectMapper()
  objectMapper.registerModule(DefaultScalaModule)
  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def toJsonString(value: Any): String = {
    objectMapper.writeValueAsString(value)
  }

  def toMap(json: Any): Map[String, Any] = {
    objectMapper.readValue(json.asInstanceOf[String], classOf[Map[String, Any]])
  }
}
