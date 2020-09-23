import {ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormBuilder} from "@angular/forms";
import {CalendarEvent, CalendarEventTitleFormatter, CalendarView} from 'angular-calendar';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {MatDialog} from '@angular/material/dialog';
import {MeetingDialodComponent} from "../meeting-form-dialog/meeting-dialod.component";
import * as moment from 'moment';
import {MeetingListItemModel} from "../../models/meetingListItem.model";


const colors: any = {
    red: {
        primary: '#ad2121',
        secondary: '#FAE3E3',
    },
    blue: {
        primary: '#1e90ff',
        secondary: '#D1E8FF',
    },
    yellow: {
        primary: '#e3bc08',
        secondary: '#FDF1BA',
    },
};

@Component({
    selector: 'app-meeting-reservation-form',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './meeting-reservation-form.component.html',
    providers: [
        {
            provide: CalendarEventTitleFormatter,
        },
    ],
    styleUrls: ['./meeting-reservation-form.component.css'],
    encapsulation: ViewEncapsulation.None,
})
export class MeetingReservationFormComponent implements OnInit {
    meetingList: Array<MeetingListItemModel>;
    displayingMeetingList: Array<CalendarEvent>;
    userId: number;

    view: CalendarView;
    viewDate: Date;
    events: CalendarEvent[];
    clickedDate: Date;

    constructor(private userService: UserService,
                private formBuilder: FormBuilder,
                private modalService: NgbModal,
                private dialog: MatDialog,) {

        this.userService.refreshCalendar.subscribe(
            () => this.ngOnInit()
        )
    }

    ngOnInit(): void {
        this.loadMeetingList();
    }

    loadCalendar() {
        this.view = CalendarView.Week;
        this.viewDate = new Date();
    }

    loadMeetingList() {
        this.userId = +localStorage.getItem('userId');
        this.meetingList = [];
        this.displayingMeetingList = [];
        //this.events = [];
        this.userService.getMeetingList(this.userId).subscribe(
            (list) => {
                this.meetingList = list;
                this.addToDisplayList();
            },
            error => console.log(error),
            () => {
                console.log(this.meetingList);
                this.loadCalendar()
            }
        )
    }

    clickOnHourSegment(date: Date) {
        this.clickedDate = date;
        const format = "YYYY-MM-DD HH:mm:ss";
        let usingDate = moment(date).format(format).toString();
        this.userService.addStartDate(usingDate);

        const dialogRef = this.dialog.open(MeetingDialodComponent);

        dialogRef.afterClosed().subscribe(result => {
            console.log(`Dialog result: ${result}`);
        });
    }

    private addToDisplayList() {
        this.events = [];
        this.meetingList.forEach(meeting => {
            const meetingData: CalendarEvent = {
                title: meeting.title + ' room: ' + meeting.meetingRoomName,
                start: new Date(meeting.startDate),
                end: new Date(meeting.endDate),
                color: colors.blue
            }
            this.events.push(meetingData);
            this.displayingMeetingList.push(meetingData);
            console.log(this.displayingMeetingList);
        })
    }

}
