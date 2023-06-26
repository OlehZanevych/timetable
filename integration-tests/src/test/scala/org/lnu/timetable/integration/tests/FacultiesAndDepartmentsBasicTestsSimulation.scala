package org.lnu.timetable.integration.tests

import org.lnu.timetable.integration.tests.cases.TestCases
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*

import java.util.concurrent.ThreadLocalRandom

class FacultiesAndDepartmentsBasicTestsSimulation extends Simulation {
  val httpProtocol = http.baseUrl("http://localhost:8080/")

  val users = scenario("Faculties and departments basic testing").exec(TestCases.testCases)

  setUp(
    users.inject(atOnceUsers(100))
  ).protocols(httpProtocol)
}
