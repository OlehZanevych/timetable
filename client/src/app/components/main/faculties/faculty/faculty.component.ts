import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FacultyService} from 'src/app/services/FacultyService';
import {Faculty} from 'src/app/model/faculty/Faculty';
import {HOST_URL} from 'src/app/api';

@Component({
  selector: 'app-faculty',
  templateUrl: './faculty.component.html',
  styleUrls: ['./faculty.component.scss']
})
export class FacultyComponent implements OnInit {

  constructor(private route: ActivatedRoute, private facultyService: FacultyService) {
  }

  faculty: Faculty = new Faculty();

  icon = new Image();

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const facultyId = Number(idParam);
      this.facultyService.getFaculty(facultyId).subscribe(faculty => {
        const {facultyLogoUri} = faculty;
        if (facultyLogoUri) {
          this.icon.src = `${HOST_URL}/${faculty.facultyLogoUri}`;
          faculty.facultyLogoUri = `${HOST_URL}/${faculty.facultyLogoUri}`;
        }

        this.faculty = faculty;
      });
    }
  }
}
