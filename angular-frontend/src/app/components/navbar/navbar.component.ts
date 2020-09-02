import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

    isLoggedIn: boolean;
    userId: string;

    constructor(private userService: UserService) {
        this.userService.loginSubject.subscribe(()=>{
            this.isLoggedIn = localStorage.getItem('auth') ? true : false;
            this.userId = localStorage.getItem('userId');
        })
    }

    ngOnInit() {
    }

    logout() {
        this.userService.logout().subscribe(() => {
                localStorage.clear();
                this.userId = '';
                this.isLoggedIn = localStorage.getItem('auth') ? true : false;
            }
        );

    }


}
