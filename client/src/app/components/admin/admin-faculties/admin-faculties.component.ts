import { Component, OnInit } from '@angular/core';
import {FacultyService} from '../../../services/FacultyService';
import {FacultyItem} from '../../../model/faculty/FacultyItem';
import {HOST_URL} from '../../../api';

@Component({
  selector: 'app-admin-faculties',
  templateUrl: './admin-faculties.component.html',
  styleUrls: ['./admin-faculties.component.scss']
})
export class AdminFacultiesComponent implements OnInit {

  constructor(private facultyService: FacultyService) { }

  faculties: FacultyItem[] = [];

  ngOnInit(): void {
    this.facultyService.getFaculties().subscribe(faculties => {
      this.faculties = faculties;
    });
  }
}

