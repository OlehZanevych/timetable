import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-info-block',
  templateUrl: './info-block.component.html',
  styleUrls: ['./info-block.component.scss']
})
export class InfoBlockComponent implements OnInit {

  @Input()
  title = '';

  constructor() {
  }

  ngOnInit(): void {
  }
}
