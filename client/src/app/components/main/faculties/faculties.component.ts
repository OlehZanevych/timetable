import { Component, OnInit } from '@angular/core';
import {FacultyService} from 'src/app/services/FacultyService';
import {HOST_URL} from 'src/app/api';
import {FacultyItem} from 'src/app/model/faculty/FacultyItem';

@Component({
  selector: 'app-faculties',
  templateUrl: './faculties.component.html',
  styleUrls: ['./faculties.component.scss']
})
export class FacultiesComponent implements OnInit {

  constructor(private facultyService: FacultyService) { }

  faculties: FacultyItem[] = [];

  ngOnInit(): void {
    this.facultyService.getFaculties().subscribe(faculties => {
      faculties.forEach(faculty => {
        const {facultyLogoUri} = faculty;
        if (facultyLogoUri) {
          faculty.facultyLogoUri = `${HOST_URL}/${facultyLogoUri}`;
        }
      });
      this.faculties = faculties;
    });
  }
}
