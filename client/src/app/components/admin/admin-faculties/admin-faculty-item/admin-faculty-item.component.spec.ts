import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminFacultyItemComponent } from './admin-faculty-item.component';

describe('AdminFacultyItemComponent', () => {
  let component: AdminFacultyItemComponent;
  let fixture: ComponentFixture<AdminFacultyItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminFacultyItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminFacultyItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
