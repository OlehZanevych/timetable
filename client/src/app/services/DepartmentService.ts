import {Injectable} from '@angular/core';
import {Api} from 'src/app/api';
import {Observable} from 'rxjs';
import {DepartmentView} from 'src/app/model/department/DepartmentView';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  constructor(private api: Api) {
  }

  getDepartmentView(departmentId: number): Observable<DepartmentView> {
    return this.api.getDepartmentView(departmentId);
  }
}
