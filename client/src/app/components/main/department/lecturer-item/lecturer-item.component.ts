import {Component, Input, OnInit} from '@angular/core';
import {LecturerItem} from 'src/app/model/lecturer/LecturerItem';

@Component({
  selector: 'app-lecturer-item',
  templateUrl: './lecturer-item.component.html',
  styleUrls: ['./lecturer-item.component.scss']
})
export class LecturerItemComponent implements OnInit {

  @Input()
  lecturer = new LecturerItem();

  constructor() { }

  ngOnInit(): void {
  }

}
