import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";

@Component({
    selector: 'app-meeting-update-dialog',
    templateUrl: './meeting-update-dialog.component.html',
    styleUrls: ['./meeting-update-dialog.component.css']
})
export class MeetingUpdateDialogComponent implements OnInit {

    meetingId: number;

    constructor(private userService: UserService) {
    }

    ngOnInit(): void {
        this.meetingId = this.userService.getMeetingId();
        this.loadActualMeeting(this.meetingId);
    }

    loadActualMeeting(meetingId: number) {

    }

    saveUpdate() {

    }
}
