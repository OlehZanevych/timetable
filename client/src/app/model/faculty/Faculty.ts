import {FacultyContent} from './FacultyContent';
import {DepartmentItem} from 'src/app/model/department/DepartmentItem';

export class Faculty extends FacultyContent {
  id = 0;
  facultyLogoUri = '';
  departments: DepartmentItem[] = [];
}
