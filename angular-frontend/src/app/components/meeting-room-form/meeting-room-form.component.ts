import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {handleValidationErrors} from "../../shared/validation.handler";

@Component({
    selector: 'app-meeting-room-form',
    templateUrl: './meeting-room-form.component.html',
    styleUrls: ['./meeting-room-form.component.css']
})
export class MeetingRoomFormComponent implements OnInit {

    meetingRoomForm: FormGroup;

    constructor(private userService: UserService,
                private formBuilder: FormBuilder) {

    }

    ngOnInit(): void {
        this.meetingRoomForm = this.formBuilder.group({
            name: [''],
            seats: ['']
        })

    }

    saveRoom() {
        this.userService.createMeetingRoom(this.meetingRoomForm.value).subscribe(
            () => console.log(this.meetingRoomForm),
            error => {
                handleValidationErrors(error, this.meetingRoomForm);
                console.log(error)
            },
            () => {
                this.meetingRoomForm.reset();
                this.ngOnInit()
            }
        )
    }

}
