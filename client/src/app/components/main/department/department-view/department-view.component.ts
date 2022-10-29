import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {DepartmentService} from 'src/app/services/DepartmentService';
import {DepartmentView} from 'src/app/model/department/DepartmentView';

@Component({
  selector: 'app-department-view',
  templateUrl: './department-view.component.html',
  styleUrls: ['./department-view.component.scss']
})
export class DepartmentViewComponent implements OnInit {

  department = new DepartmentView();

  constructor(private route: ActivatedRoute, private departmentService: DepartmentService) {
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      const departmentId = +idParam;
      this.departmentService.getDepartmentView(departmentId).subscribe(department => this.department = department);
    }
  }
}
