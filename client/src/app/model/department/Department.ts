import {LecturerItem} from 'src/app/model/lecturer/LecturerItem';

export class Department {
  id = 0;
  name = '';
  facultyId = 0;
  email = '';
  phone = '';
  info = '';
  lecturers: LecturerItem[] = [];
}
