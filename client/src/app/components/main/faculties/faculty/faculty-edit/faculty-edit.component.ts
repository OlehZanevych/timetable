import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FacultyService} from 'src/app/services/FacultyService';
import {Faculty} from 'src/app/model/faculty/Faculty';
import {FormBuilder, FormControl, Validators} from '@angular/forms';
import {HOST_URL} from 'src/app/api';
import {FacultyContent} from 'src/app/model/faculty/FacultyContent';
import {forkJoin} from 'rxjs';

@Component({
  selector: 'app-faculty-edit',
  templateUrl: './faculty-edit.component.html',
  styleUrls: ['./faculty-edit.component.scss']
})
export class FacultyEditComponent implements OnInit {
  logoUri = '';
  facultyForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(5)]],
    website: ['', [Validators.required, Validators.minLength(5)]],
    email: ['', [Validators.required, Validators.minLength(5)]],
    phone: ['', [Validators.required, Validators.minLength(5)]],
    address: ['', [Validators.required, Validators.minLength(5)]],
    info: [''],
    logoFile: [null, Validators.required],
    fileSource: new FormControl<string | ArrayBuffer | null>(null, {validators: [Validators.required]})
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private facultyService: FacultyService) {
  }

  @Input()
  faculty: Faculty = new Faculty();

  facultyId = 0;

  ngOnInit(): void {
    this.loadFaculty();
  }

  loadFaculty(): void {
    this.facultyId = Number(this.route.snapshot.paramMap.get('id'));
    this.facultyService.getFaculty(this.facultyId).subscribe(faculty => {
      const {facultyLogoUri} = faculty;
      if (facultyLogoUri) {
        this.logoUri = `${HOST_URL}/${faculty.facultyLogoUri}`;
      }

      this.facultyForm.setValue({
        name: faculty.name,
        website: faculty.website,
        email: faculty.email,
        phone: faculty.phone,
        address: faculty.address,
        info: faculty.info,
        logoFile: null,
        fileSource: ''
      });

      this.faculty = faculty;
    });
  }

  updateFacultyContent(): void {
    const formValue = this.facultyForm.value;

    const facultyContent = new FacultyContent();
    facultyContent.name = formValue.name!;
    facultyContent.website = formValue.website!;
    facultyContent.email = formValue.email!;
    facultyContent.phone = formValue.phone!;
    facultyContent.address = formValue.address!;
    facultyContent.info = formValue.info!;

    const {logoFile} = formValue;

    const calls = [this.facultyService.updateFaculty(this.facultyId, facultyContent)];
    if (logoFile) {
      calls.push(this.facultyService.updateFacultyLogo(this.facultyId, logoFile));
    }

    forkJoin(calls).subscribe(() => {
      this.router.navigate(['faculties', this.faculty.id]).then();
    });
  }

  get f() {
    return this.facultyForm.controls;

  }

  onFileChange(event: any): void {
    const reader = new FileReader();
    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.logoUri = reader.result as string;

        this.facultyForm.patchValue({
          logoFile: file,
          fileSource: reader.result
        });
      };
    }
  }

  onSubmit(): void {
    this.updateFacultyContent();
  }
}
