import {Component, Input, OnInit} from '@angular/core';
import {FacultyItem} from 'src/app/model/faculty/FacultyItem';

@Component({
  selector: 'app-faculty-item',
  templateUrl: './faculty-item.component.html',
  styleUrls: ['./faculty-item.component.scss']
})
export class FacultyItemComponent implements OnInit {
  @Input()
  faculty = new FacultyItem();

  constructor() {
  }

  ngOnInit(): void {
  }
}
