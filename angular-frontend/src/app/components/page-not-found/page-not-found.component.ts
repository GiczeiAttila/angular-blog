import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
             selector   : 'app-page-not-found',
             templateUrl: './page-not-found.component.html',
             styleUrls  : ['./page-not-found.component.css'],
           })
export class PageNotFoundComponent implements OnInit {

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
      if (localStorage.getItem('auth')) {
          this.userService.loginSubject.next();
      }
  }
}
