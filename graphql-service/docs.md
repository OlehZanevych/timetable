# Examples of GraphQL queries and mutations

## Faculties

### Get a list of Faculties

#### Query:
```angular2html
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
        hasNextPage
      }
    }
  }
}
```

### Get a list of Faculties (with limit and offset)

#### Query:
```angular2html
query FacultyConnection($limit: Int!, $offset: Int) {
  faculties {
    facultyConnection(limit: $limit, offset: $offset) {
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
        hasNextPage
      }
    }
  }
}
```

#### Variables:
```angular2html
{
  "limit": 10,
  "offset": 20
}
```

### Get Faculty by ID

#### Query:
```angular2html
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
```

#### Variables:
```angular2html
{
  "id": "13"
}
```

### Create Faculty

#### Query:
```angular2html
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
```

#### Variables:
```angular2html
{
  "faculty": {
    "name": "Faculty of Applied Mathematics and Informatics",
    "website": "ami.lnu.edu.ua",
    "email": "ami@lnu.edu.ua",
    "phone": "274-01-80, 239-41-86",
    "address": "Universytetska Street 1, Lviv, 79000, Ukraine",
    "info": "Faculty of Applied Mathematics and Informatics is ..."
  }
}
```

### Update Faculty

#### Query:
```angular2html
mutation UpdateFaculty($id: ID!, $faculty: FacultyInputPayload!) {
  faculties {
    updateFaculty(id: $id, faculty: $faculty) {
      isSuccess
      errorStatus
    }
  }
}
```

#### Variables:
```angular2html
{
  "id": "13",
  "faculty": {
    "name": "Faculty of Applied Mathematics and Informatics",
    "website": "ami.lnu.edu.ua",
    "email": "ami@lnu.edu.ua",
    "phone": "274-01-80, 239-41-86",
    "address": "Universytetska Street 1, Lviv, 79000, Ukraine",
    "info": "Faculty of Applied Mathematics and Informatics is ..."
  }
}
```

### Delete Faculty

#### Query:
```angular2html
mutation DeleteFaculty($id: ID!) {
  faculties {
    deleteFaculty(id: $id) {
      isSuccess
      errorStatus
    }
  }
}
```

#### Variables:
```angular2html
{
  "id": "13"
}
```

## Departments

### Get a list of Departments

#### Query:
```angular2html
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
        hasNextPage
      }
    }
  }
}
```

### Get a list of Departments (with limit and offset)

#### Query:
```angular2html
query DepartmentConnection($limit: Int!, $offset: Int!) {
  departments {
    departmentConnection(limit: $limit, offset: $offset) {
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
        hasNextPage
      }
    }
  }
}
```

#### Variables:
```angular2html
{
  "limit": 10,
  "offset": 20
}
```

### Get Department by ID

#### Query:
```angular2html
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
```

#### Variables:
```angular2html
{
  "id": 21
}
```

### Create Department

#### Query:
```angular2html
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
```

#### Variables:
```angular2html
{
  "department": {
    "name": "Department of Applied Mathematics",
    "facultyId": "13",
    "email": "kpm@lnu.edu.ua",
    "phone": "(032) 239-41-78",
    "info": "Department of Applied Mathematics was founded in 1963. From 1963 to 1988 it was headed by Doctor of Technical Sciences Professor N. P. Fleishman. Since 1988 the Head of the Department is Doctor of Science, Professor, Distinguished Professor of Ivan Franko National University of Lviv, Honored Worker of Education of Ukraine Ya. H. Savula. The Department formed a scientific field of problems of mathematical and computer modeling and optimization of physical – mechanical fields in solid environment. This research area is based on the scientific achievements of professors N. P. Fleyshman and Ya. H. Savula and their students. The modern period of Departments functionality under Professor Ya. H. Savula direction studies characterized variation- projection methods, finite and boundary elements and their application to the analysis and optimal management of physical – mechanical fields. Scientific work is being held on the Department in the field of numerical modeling of solutions of applied problems of different nature from heterogeneous mathematical models and using hybrid schemes of finite and boundary elements methods, in the numerical analysis of nonlinear problems of mathematical physics with application to shell theory, the theory of plasticity and to modeling of transport processes plasma. Scientific researches are focused on their use in science, technology and use for modeling in ecology and medicine."
  }
}
```

### Update Department

#### Query:
```angular2html
mutation UpdateDepartment($id: ID!, $department: DepartmentInputPayload!) {
  departments {
    updateDepartment(id: $id, department: $department) {
      isSuccess
      errorStatus
    }
  }
}
```

#### Variables:
```angular2html
{
  "id": "21",
  "department": {
    "name": "Department of Applied Mathematics",
    "facultyId": "13",
    "email": "kpm@lnu.edu.ua",
    "phone": "(032) 239-41-78",
    "info": "Department of Applied Mathematics was founded in 1963. From 1963 to 1988 it was headed by Doctor of Technical Sciences Professor N. P. Fleishman. Since 1988 the Head of the Department is Doctor of Science, Professor, Distinguished Professor of Ivan Franko National University of Lviv, Honored Worker of Education of Ukraine Ya. H. Savula. The Department formed a scientific field of problems of mathematical and computer modeling and optimization of physical – mechanical fields in solid environment. This research area is based on the scientific achievements of professors N. P. Fleyshman and Ya. H. Savula and their students. The modern period of Departments functionality under Professor Ya. H. Savula direction studies characterized variation- projection methods, finite and boundary elements and their application to the analysis and optimal management of physical – mechanical fields. Scientific work is being held on the Department in the field of numerical modeling of solutions of applied problems of different nature from heterogeneous mathematical models and using hybrid schemes of finite and boundary elements methods, in the numerical analysis of nonlinear problems of mathematical physics with application to shell theory, the theory of plasticity and to modeling of transport processes plasma. Scientific researches are focused on their use in science, technology and use for modeling in ecology and medicine."
  }
}
```

### Delete Department

#### Query:
```angular2html
mutation DeleteDepartment($id: ID!) {
  departments {
    deleteDepartment(id: $id) {
      isSuccess
      errorStatus
    }
  }
}
```

#### Variables:
```angular2html
{
  "id": "21"
}
```
