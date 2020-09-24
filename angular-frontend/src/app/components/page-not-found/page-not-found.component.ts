import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
             selector   : 'app-page-not-found',
             templateUrl: './page-not-found.component.html',
             styleUrls  : ['./page-not-found.component.css'],
           })
export class PageNotFoundComponent implements OnInit {

  constructor(private userService: UserService,private router: Router) {
  }

  ngOnInit(): void {
      if (localStorage.getItem('auth')) {
          this.userService.loginSubject.next();
      }else {
          this.router.navigate(['']);
      }
  }
}
