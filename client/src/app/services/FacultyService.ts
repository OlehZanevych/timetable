import {Injectable} from '@angular/core';
import { Apollo, gql } from 'apollo-angular'
import {Api} from 'src/app/api';
import {map, Observable} from 'rxjs';
import {FacultyItem} from 'src/app/model/faculty/FacultyItem';
import {Faculty} from 'src/app/model/faculty/Faculty';
import {FacultyContent} from 'src/app/model/faculty/FacultyContent';


@Injectable({
  providedIn: 'root'
})
export class FacultyService {
  constructor(private api: Api, private apollo: Apollo) {
  }

  getFaculties(): Observable<FacultyItem[]> {
    return this.apollo.watchQuery({
      query: gql`
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
              }
            }
          }
        }
      `
    })
    .valueChanges.pipe(map((response: any) =>
      response.data?.faculties?.facultyConnection?.nodes || []));
  }

  getFaculty(facultyId: number): Observable<Faculty> {
    return this.apollo.watchQuery({
      query: gql`
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
      `,
      variables: {
        id: facultyId
      }
    })
    .valueChanges.pipe(map((response: any) =>
      response.data?.faculties?.faculty || []));
  }

  updateFaculty(facultyId: number, facultyContent: FacultyContent): Observable<void> {
    return this.api.updateFaculty(facultyId, facultyContent);
  }

  updateFacultyLogo(facultyId: number, facultyLogo: File): Observable<void> {
    return this.api.updateFacultyLogo(facultyId, facultyLogo);
  }
}
