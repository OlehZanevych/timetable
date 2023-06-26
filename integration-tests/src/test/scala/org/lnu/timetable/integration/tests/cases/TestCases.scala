package org.lnu.timetable.integration.tests.cases

import io.gatling.core.Predef._

object TestCases {
  val testCases = exec(FacultiesAndDepartmentsBasicTestCases.testCase)
}
