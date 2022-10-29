import {Injectable} from '@angular/core';
import {Api} from 'src/app/api';
import {Observable} from 'rxjs';
import {FacultyItem} from 'src/app/model/faculty/FacultyItem';
import {Faculty} from 'src/app/model/faculty/Faculty';
import {FacultyContent} from 'src/app/model/faculty/FacultyContent';

@Injectable({
  providedIn: 'root'
})
export class FacultyService {
  constructor(private api: Api) {
  }

  getFaculties(): Observable<FacultyItem[]> {
    return this.api.getFaculties();
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
