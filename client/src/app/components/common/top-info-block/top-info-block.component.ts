import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-top-info-block',
  templateUrl: './top-info-block.component.html',
  styleUrls: ['./top-info-block.component.scss']
})
export class TopInfoBlockComponent implements OnInit {

  @Input()
  imageUri = '';

  @Input()
  imageAlt = '';

  constructor() {
  }

  ngOnInit(): void {
  }

}
