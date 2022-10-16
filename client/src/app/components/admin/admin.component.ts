import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import { BreakpointObserver } from '@angular/cdk/layout';
import {MatSidenav} from '@angular/material/sidenav';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;

  constructor(private observer: BreakpointObserver, private router: Router) { }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.observer.observe(['(min-width: 1024px)']).subscribe((res) => {
      if (res.matches) {
        this.sidenav.mode = 'side';
        this.sidenav.open();
      } else {
        this.sidenav.mode = 'over';
        this.sidenav.close();
      }
    });
  }
}
