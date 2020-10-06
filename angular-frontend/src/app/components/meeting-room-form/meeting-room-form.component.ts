import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {handleValidationErrors} from "../../shared/validation.handler";
import {Router} from "@angular/router";
import {HelperService} from "../../services/helper.service";
import {MeetingRoomOptionDtoModel} from "../../models/meetingRoomOptionDto.model";

@Component({
    selector: 'app-meeting-room-form',
    templateUrl: './meeting-room-form.component.html',
    styleUrls: ['./meeting-room-form.component.css']
})
export class MeetingRoomFormComponent implements OnInit {

    meetingRoomForm: FormGroup;
    activeMeetingRooms: Array<MeetingRoomOptionDtoModel>;
    index: number;

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
        this.index = 1;
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }else {
            this.router.navigate(['']);
        }

        this.loadMeetingRooms();
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

    loadMeetingRooms() {
        this.userService.fetchMeetingRoomListInitData().subscribe(
            (list) => this.activeMeetingRooms = list,
            error => console.log(error)
        )
    }

    deleteMeetingRoom(roomId: number) {
        this.userService.deleteMeetingRoom(roomId).subscribe(
            () => this.ngOnInit(),
            error => console.log(error)
        )
    }
}
