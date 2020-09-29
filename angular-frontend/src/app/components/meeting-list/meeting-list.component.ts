import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {UserMeetingReservationListItemModel} from "../../models/userMeetingReservationListItem.model";
import {Router} from "@angular/router";
import {MeetingStatusChangeDtoModel} from "../../models/meetingStatusChangeDto.model";

@Component({
    selector: 'app-meeting-list',
    templateUrl: './meeting-list.component.html',
    styleUrls: ['./meeting-list.component.css']
})
export class MeetingListComponent implements OnInit {

    meetingList: Array<UserMeetingReservationListItemModel>;
    displayMeetingList: Array<UserMeetingReservationListItemModel>;
    userId: number;
    index: number;


    constructor(private userService: UserService,
                private router: Router) {
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        } else {
            this.router.navigate(['']);
        }
        this.index = 0;
        this.loadMeetingList();

    }

    loadMeetingList() {
        this.userId = +localStorage.getItem('userId');
        this.meetingList = [];
        this.displayMeetingList = [];
        this.userService.getUserMeetingList(this.userId).subscribe(
            (list) => {
                list.forEach(meeting => {
                    meeting.startDate = meeting.startDate.toString().replace('T', ' ');
                    meeting.endDate = meeting.endDate.toString().replace('T', ' ');
                    this.displayMeetingList.push(meeting);

                    meeting.startDate = meeting.startDate.slice(0, 16);
                    meeting.endDate = meeting.endDate.slice(11, 16);
                    this.meetingList.push(meeting);
                })
            },
            error => console.log(error)
        )
    }


    editMeeting(meetingId: number) {

    }

    deleteMeeting(meetingId: number) {
        this.changeMeetingStatus('DELETED', meetingId);
    }

    changeToActive(meetingId: number) {
        this.changeMeetingStatus('ACTIVE', meetingId);
    }

    changeMeetingStatus(newStatus: string, meetingId: number) {
        let statusChangeData: MeetingStatusChangeDtoModel = {
            meetingId: meetingId,
            status: newStatus
        }

        this.userService.changeMeetingStatus(statusChangeData).subscribe(
            () => this.ngOnInit()
        )
    }
}
