import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {handleValidationErrors} from "../../shared/validation.handler";
import {Router} from "@angular/router";
import {HelperService} from "../../services/helper.service";

@Component({
    selector: 'app-meeting-room-form',
    templateUrl: './meeting-room-form.component.html',
    styleUrls: ['./meeting-room-form.component.css']
})
export class MeetingRoomFormComponent implements OnInit {

    meetingRoomForm: FormGroup;

    constructor(private userService: UserService,
                private formBuilder: FormBuilder,
                private router: Router,
                private helperService: HelperService) {
        this.meetingRoomForm = this.formBuilder.group({
            name: [''],
            seats: ['']
        })
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }else {
            this.router.navigate(['']);
        }


    }

    saveRoom() {
        this.userService.createMeetingRoom(this.meetingRoomForm.value).subscribe(
            () => console.log(this.meetingRoomForm),
            error => {
                handleValidationErrors(error, this.meetingRoomForm);
                console.log(error)
            },
            () => {
                this.helperService.resetForm(this.meetingRoomForm);
            }
        )
    }

}
