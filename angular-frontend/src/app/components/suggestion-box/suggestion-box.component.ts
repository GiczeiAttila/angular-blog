import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
    selector: 'app-suggestion-box',
    templateUrl: './suggestion-box.component.html',
    styleUrls: ['./suggestion-box.component.css']
})
export class SuggestionBoxComponent implements OnInit {

    activeIndex: number = 0;
    constructor(private userService: UserService) {
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }
    }

}
