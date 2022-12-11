import {Component, Input, OnInit} from '@angular/core';
import {DepartmentItem} from 'src/app/model/department/DepartmentItem';

@Component({
  selector: 'app-department-item',
  templateUrl: './department-item.component.html',
  styleUrls: ['./department-item.component.scss']
})
export class DepartmentItemComponent implements OnInit {

  @Input()
  department = new DepartmentItem();

  constructor() { }

  ngOnInit(): void {
  }

}
