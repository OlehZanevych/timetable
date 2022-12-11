import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Faculty} from 'src/app/model/faculty/Faculty';
import {FacultyItem} from 'src/app/model/faculty/FacultyItem';
import {Observable} from 'rxjs';
import {FacultyContent} from 'src/app/model/faculty/FacultyContent';
import {DepartmentView} from 'src/app/model/department/DepartmentView';

export const HOST_URL = 'http://localhost:8080';

const DEPARTMENTS_URL = `${HOST_URL}/departments`;
const FACULTIES_URL = `${HOST_URL}/faculties`;
const FACULTY_ITEMS_URL = `${FACULTIES_URL}/items`;

@Injectable({
  providedIn: 'root'
})
export class Api {
  constructor(private httpClient: HttpClient) {
  }

  getDepartmentView(departmentId: number): Observable<DepartmentView> {
    return this.httpClient.get<DepartmentView>(`${DEPARTMENTS_URL}/${departmentId}/view`);
  }

  getFaculties(): Observable<FacultyItem[]> {
    return this.httpClient.get<FacultyItem[]>(FACULTY_ITEMS_URL);
  }

  getFaculty(facultyId: number): Observable<Faculty> {
    return this.httpClient.get<Faculty>(`${FACULTIES_URL}/${facultyId}`);
  }

  updateFaculty(facultyId: number, facultyContent: FacultyContent): Observable<void> {
    return this.httpClient.put<void>(`${FACULTIES_URL}/${facultyId}`, facultyContent);
  }

  updateFacultyLogo(facultyId: number, facultyLogo: File): Observable<void> {
    const headers = new HttpHeaders().set('Content-Type', 'multipart/form-data');

    const formData = new FormData();
    formData.append('imageFile', facultyLogo);

    const options = {headers};

    return this.httpClient.post<void>(`${FACULTIES_URL}/${facultyId}/logo`, formData);
  }
}
