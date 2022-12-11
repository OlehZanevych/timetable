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
    return this.apollo
    .watchQuery({
      query: gql`
        query FacultyConnection {
          faculties {
            facultyConnection {
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
                address
                info
              }
            }
          }
        }
      `
    })
    .valueChanges.pipe(map((response: any) =>
      response.data?.faculties?.facultyConnection || []));
  }

  getFaculty(facultyId: number): Observable<Faculty> {
    return this.api.getFaculty(facultyId);
  }

  updateFaculty(facultyId: number, facultyContent: FacultyContent): Observable<void> {
    return this.api.updateFaculty(facultyId, facultyContent);
  }

  updateFacultyLogo(facultyId: number, facultyLogo: File): Observable<void> {
    return this.api.updateFacultyLogo(facultyId, facultyLogo);
  }
}
