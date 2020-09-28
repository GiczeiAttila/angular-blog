import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {UserMeetingReservationListItemModel} from "../../models/userMeetingReservationListItem.model";

@Component({
    selector: 'app-meeting-list',
    templateUrl: './meeting-list.component.html',
    styleUrls: ['./meeting-list.component.css']
})
export class MeetingListComponent implements OnInit {

    meetingList: Array<UserMeetingReservationListItemModel>;
    loadMeetingList

    constructor(private userService: UserService) {
    }

    ngOnInit(): void {

    }


}
