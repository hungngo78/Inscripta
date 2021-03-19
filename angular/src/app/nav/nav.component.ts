import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';


@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {

  opened: boolean = true;
  @ViewChild('sidenav') public sideNav: MatSidenav | undefined;

  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  home() {
    this.router.navigate(['home']);
    this.sideNav?.close();
  }

  requestEdit() {
    //throw "Not implemented";
    this.router.navigate(['edit-request']);
    this.sideNav?.close();
  }

}
