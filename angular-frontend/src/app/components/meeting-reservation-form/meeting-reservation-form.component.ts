import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
    selector: 'app-meeting-reservation-form',
    templateUrl: './meeting-reservation-form.component.html',
    styleUrls: ['./meeting-reservation-form.component.css']
})
export class MeetingReservationFormComponent implements OnInit {

    constructor(private userService: UserService) {
    }

    ngOnInit(): void {


    }

}
