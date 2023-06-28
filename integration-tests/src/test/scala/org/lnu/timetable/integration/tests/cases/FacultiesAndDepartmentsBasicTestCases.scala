package org.lnu.timetable.integration.tests.cases

import io.gatling.core.Predef.*
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef.*
import io.gatling.http.request.builder.HttpRequestBuilder

import java.util.UUID
import org.lnu.timetable.integration.tests.util.JsonUtil

object FacultiesAndDepartmentsBasicTestCases {

  val getFacultyListQuery =
    """
    query FacultyConnection {
      faculties {
        facultyConnection {
          nodes {
            id
            name
            website
            email
            phone
            address
            logoUri
            info
          }
          pageInfo {
            total
            nextPageOffset
            hasNextPage
          }
        }
      }
    }
"""

  val getFacultyListWithDepartmentsQuery =
    """
    query FacultyConnection {
      faculties {
        facultyConnection {
          nodes {
            id
            name
            website
            email
            phone
            address
            logoUri
            info
            departments {
              id
              name
              email
              phone
              info
            }
          }
          pageInfo {
            total
            nextPageOffset
            hasNextPage
          }
        }
      }
    }
"""

  val getFacultyQuery =
    """
    query Faculty($id: ID!) {
      faculties {
        faculty(id: $id) {
          id
          name
          website
          email
          phone
          address
          logoUri
          info
        }
      }
    }
"""

  val getFacultyWithDepartmentsQuery =
    """
    query Faculty($id: ID!) {
      faculties {
        faculty(id: $id) {
          id
          name
          website
          email
          phone
          address
          logoUri
          info
          departments {
            id
            name
            email
            phone
            info
          }
        }
      }
    }
"""

  val createFacultyMutation =
    """
    mutation CreateFaculty($faculty: FacultyInputPayload!) {
      faculties {
        createFaculty(faculty: $faculty) {
          isSuccess
          data {
            id
            name
            website
            email
            phone
            address
            info
          }
          errorStatus
        }
      }
    }
"""

  val updateFacultyMutation =
    """
    mutation UpdateFaculty($id: ID!, $faculty: FacultyInputPayload!) {
      faculties {
        updateFaculty(id: $id, faculty: $faculty) {
          isSuccess
          errorStatus
        }
      }
    }
"""

  val deleteFacultyMutation =
    """
    mutation DeleteFaculty($id: ID!) {
      faculties {
        deleteFaculty(id: $id) {
          isSuccess
          errorStatus
        }
      }
    }
  """

  val getDepartmentsListQuery =
    """
    query DepartmentConnection {
      departments {
        departmentConnection {
          nodes {
            id
            name
            email
            phone
            info
          }
          pageInfo {
            total
            nextPageOffset
            hasNextPage
          }
        }
      }
    }
"""

  val getDepartmentsListWithFacultyQuery =
    """
    query DepartmentConnection {
      departments {
        departmentConnection {
          nodes {
            id
            name
            email
            phone
            info
            faculty {
              id
              name
              website
              email
              phone
              address
              logoUri
              info
            }
          }
          pageInfo {
            total
            nextPageOffset
            hasNextPage
          }
        }
      }
    }
"""

  val getDepartmentQuery =
    """
    query Department($id: ID!) {
      departments {
        department(id: $id) {
          id
          name
          email
          phone
          info
        }
      }
    }
"""

  val getDepartmentWithFacultyQuery =
    """
    query Department($id: ID!) {
      departments {
        department(id: $id) {
          id
          name
          email
          phone
          info
          faculty {
            id
            name
            website
            email
            phone
            address
            logoUri
            info
          }
        }
      }
    }
"""

  val createDepartmentMutation =
    """
    mutation CreateDepartment($department: DepartmentInputPayload!) {
      departments {
        createDepartment(department: $department) {
          isSuccess
          data {
            id
            name
            email
            phone
            info
            faculty {
              id
              name
              website
              email
              phone
              address
              logoUri
              info
            }
          }
          errorStatus
        }
      }
    }
"""

  val updateDepartmentMutation =
    """
    mutation UpdateDepartment($id: ID!, $department: DepartmentInputPayload!) {
      departments {
        updateDepartment(id: $id, department: $department) {
          isSuccess
          errorStatus
        }
      }
    }
"""

  val deleteDepartmentMutation =
    """
    mutation DeleteDepartment($id: ID!) {
      departments {
        deleteDepartment(id: $id) {
          isSuccess
          errorStatus
        }
      }
    }
"""

  def testCase = exec()
    .exec(session => {
      session
        .set("facultyNameSuffix", uuid())
        .set("departmentNameSuffix", uuid())
    })
    .exec(buildGraphQlRequest("Create Faculty", createFacultyMutation, Map(
      "faculty" -> Map(
        "name" -> "Faculty of Applied Mathematics and Informatics ${facultyNameSuffix}",
        "website" -> "ami.lnu.edu.ua",
        "email" -> "ami@lnu.edu.ua",
        "phone" -> "274-01-80, 239-41-86",
        "address" -> "Universytetska Street 1, Lviv, 79000, Ukraine",
        "info" -> "Faculty of Applied Mathematics and Informatics is ..."
      )
    ))
      .check(jsonPath("$.data.faculties.createFaculty.isSuccess").is("true"))
      .check(jsonPath("$.data.faculties.createFaculty.errorStatus").isNull)
      .check(jsonPath("$.data.faculties.createFaculty.data.id").saveAs("facultyId")))

    .exec(buildGraphQlRequest("Update Faculty", updateFacultyMutation, Map(
      "id" -> "${facultyId}",
      "faculty" -> Map(
        "name" -> "Faculty of Applied Mathematics and Informatics ${facultyNameSuffix}2",
        "website" -> "ami2.lnu.edu.ua",
        "email" -> "ami2@lnu.edu.ua",
        "phone" -> "274-01-80, 239-41-87",
        "address" -> "Universytetska Street 2, Lviv, 79000, Ukraine",
        "info" -> "Faculty of Applied Mathematics and Informatics is the best of the best!"
      )
    ))
      .check(jsonPath("$.data.faculties.updateFaculty.isSuccess").is("true"))
      .check(jsonPath("$.data.faculties.updateFaculty.errorStatus").isNull)
    )

    .exec(buildGraphQlRequest("Create Department", createDepartmentMutation, Map(
      "department" -> Map(
        "name" -> "Department of Applied Mathematics 1 ${departmentNameSuffix}",
        "facultyId" -> "${facultyId}",
        "email" -> "kpm@lnu.edu.ua",
        "phone" -> "(032) 239-41-78",
        "info" -> "Department of Applied Mathematics 1 is ..."
      )
    ))
      .check(jsonPath("$.data.departments.createDepartment.isSuccess").is("true"))
      .check(jsonPath("$.data.departments.createDepartment.errorStatus").isNull)
    )

    .exec(buildGraphQlRequest("Create Department", createDepartmentMutation, Map(
      "department" -> Map(
        "name" -> "Department of Applied Mathematics 2 ${departmentNameSuffix}",
        "facultyId" -> "${facultyId}",
        "email" -> "kpm2@lnu.edu.ua",
        "phone" -> "(032) 239-41-79",
        "info" -> "Department of Applied Mathematics 2 is ..."
      )
    ))
      .check(jsonPath("$.data.departments.createDepartment.isSuccess").is("true"))
      .check(jsonPath("$.data.departments.createDepartment.errorStatus").isNull)
    )

    .exec(buildGraphQlRequest("Create Department", createDepartmentMutation, Map(
      "department" -> Map(
        "name" -> "Department of Applied Mathematics 3 ${departmentNameSuffix}",
        "facultyId" -> "${facultyId}",
        "email" -> "kpm@3lnu.edu.ua",
        "phone" -> "(032) 239-41-80",
        "info" -> "Department of Applied Mathematics 3 is ..."
      )
    ))
      .check(jsonPath("$.data.departments.createDepartment.isSuccess").is("true"))
      .check(jsonPath("$.data.departments.createDepartment.errorStatus").isNull)
      .check(jsonPath("$.data.departments.createDepartment.data.id").saveAs("departmentId"))
    )

    .exec(buildGraphQlRequest("Create Department", createDepartmentMutation, Map(
      "department" -> Map(
        "name" -> "Department of Applied Mathematics 4 ${departmentNameSuffix}",
        "facultyId" -> "${facultyId}",
        "email" -> "kpm4@lnu.edu.ua",
        "phone" -> "(032) 239-41-81",
        "info" -> "Department of Applied Mathematics 4 is ..."
      )
    ))
      .check(jsonPath("$.data.departments.createDepartment.isSuccess").is("true"))
      .check(jsonPath("$.data.departments.createDepartment.errorStatus").isNull)
    )

    .exec(buildGraphQlRequest("Create Department", createDepartmentMutation, Map(
      "department" -> Map(
        "name" -> "Department of Applied Mathematics 5 ${departmentNameSuffix}",
        "facultyId" -> "${facultyId}",
        "email" -> "kpm5@lnu.edu.ua",
        "phone" -> "(032) 239-41-82",
        "info" -> "Department of Applied Mathematics 5 is ..."
      )
    ))
      .check(jsonPath("$.data.departments.createDepartment.isSuccess").is("true"))
      .check(jsonPath("$.data.departments.createDepartment.errorStatus").isNull)
    )

    .exec(buildGraphQlRequest("Update Department", updateDepartmentMutation, Map(
      "id" -> "${departmentId}",
      "department" -> Map(
        "name" -> "Department of Applied Mathematics 3 ${departmentNameSuffix}",
        "facultyId" -> "${facultyId}",
        "email" -> "kpm@30lnu.edu.ua",
        "phone" -> "(032) 239-41-87",
        "info" -> "Department of Applied Mathematics 3 is the best of the best!"
      )
    ))
      .check(jsonPath("$.data.departments.updateDepartment.isSuccess").is("true"))
      .check(jsonPath("$.data.departments.updateDepartment.errorStatus").isNull)
    )

    .exec(buildGraphQlRequest("Get Faculty by id", getFacultyQuery, Map(
      "id" -> "${facultyId}"
    ))
      .check(jsonPath("$.data.faculties.faculty.id").is("${facultyId}"))
    )

    .exec(buildGraphQlRequest("Get Faculty by id (with Departments)", getFacultyWithDepartmentsQuery, Map(
      "id" -> "${facultyId}"
    ))
      .check(jsonPath("$.data.faculties.faculty.id").is("${facultyId}"))
    )

    .exec(buildGraphQlRequest("Get list of Faculties", getFacultyListQuery))

    .exec(buildGraphQlRequest("Get list of Faculties (with Departments)", getFacultyListWithDepartmentsQuery))

    .exec(buildGraphQlRequest("Get Department by id", getDepartmentQuery, Map(
      "id" -> "${departmentId}"
    ))
      .check(jsonPath("$.data.departments.department.id").is("${departmentId}"))
    )

    .exec(buildGraphQlRequest("Get Department by id (with Faculty)", getDepartmentWithFacultyQuery, Map(
      "id" -> "${departmentId}"
    ))
      .check(jsonPath("$.data.departments.department.id").is("${departmentId}"))
    )

    .exec(buildGraphQlRequest("Get list of Departments", getDepartmentsListQuery))

    .exec(buildGraphQlRequest("Get list of Departments (with Faculty)", getFacultyListWithDepartmentsQuery))

    .exec(buildGraphQlRequest("Delete Department", deleteDepartmentMutation, Map(
      "id" -> "${departmentId}"
    ))
      .check(jsonPath("$.data.departments.deleteDepartment.isSuccess").is("true"))
      .check(jsonPath("$.data.departments.deleteDepartment.errorStatus").isNull)
    )

    .exec(buildGraphQlRequest("Delete Faculty", deleteFacultyMutation, Map(
      "id" -> "${facultyId}"
    ))
      .check(jsonPath("$.data.faculties.deleteFaculty.isSuccess").is("true"))
      .check(jsonPath("$.data.faculties.deleteFaculty.errorStatus").isNull)
    )

  def buildGraphQlRequest(queryName: String, query: String, variables: Map[String, Any] = Map()): HttpRequestBuilder = {
    val body = Map(
      "query" -> query,
      "variables" -> variables
    )
    val bodyJsonString = JsonUtil.toJsonString(body)

    http(queryName)
      .post("graphql")
      .header("Content-Type", "application/json")
      .body(StringBody(bodyJsonString))
      .check(status.is(200))
      .check(jsonPath("$.errors").notExists)
  }

  def uuid(): String = {
    UUID.randomUUID().toString().toUpperCase();
  }
}
