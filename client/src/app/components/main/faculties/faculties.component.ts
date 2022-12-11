import {Component, OnInit} from '@angular/core';
import {FacultyService} from 'src/app/services/FacultyService';
import {FacultyItem} from 'src/app/model/faculty/FacultyItem';

@Component({
  selector: 'app-faculties',
  templateUrl: './faculties.component.html',
  styleUrls: ['./faculties.component.scss']
})
export class FacultiesComponent implements OnInit {

  constructor(private facultyService: FacultyService) {
  }

  faculties: FacultyItem[] = [];

  ngOnInit(): void {
    this.facultyService.getFaculties().subscribe(faculties => {
      this.faculties = faculties;
    });
  }
}
