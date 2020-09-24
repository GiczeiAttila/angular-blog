import {ChangeDetectionStrategy, Component, Injectable, OnInit, ViewEncapsulation} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {CalendarEvent, CalendarEventTitleFormatter, CalendarView} from 'angular-calendar';
import {UserForMeetingOptionDtoModel} from "../../models/userForMeetingOptionDto.model";
import {MeetingRoomOptionDtoModel} from "../../models/meetingRoomOptionDto.model";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {MatDialog} from '@angular/material/dialog';
import {MeetingDialodComponent} from "../meeting-form-dialog/meeting-dialod.component";
import * as moment from 'moment';
import {Router} from "@angular/router";


@Injectable()
export class CustomEventTitleFormatter extends CalendarEventTitleFormatter {
    weekTooltip(event: CalendarEvent, title: string) {
        if (!event.meta.tmpEvent) {
            return super.weekTooltip(event, title);
        }
    }

    dayTooltip(event: CalendarEvent, title: string) {
        if (!event.meta.tmpEvent) {
            return super.dayTooltip(event, title);
        }
    }
}

@Component({
    selector: 'app-meeting-reservation-form',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './meeting-reservation-form.component.html',
    providers: [
        {
            provide: CalendarEventTitleFormatter,
            useClass: CustomEventTitleFormatter,
        },
    ],
    styleUrls: ['./meeting-reservation-form.component.css'],
    encapsulation: ViewEncapsulation.None,
})
export class MeetingReservationFormComponent implements OnInit {


    meetingRequestForm: FormGroup;
    userList: UserForMeetingOptionDtoModel[];
    meetingRoomList: MeetingRoomOptionDtoModel[];

    actualSeats: number;

    view: CalendarView = CalendarView.Week;
    viewDate: Date = new Date();
    events: CalendarEvent[] = [];
    clickedDate: Date;
    clickedColumn: number;


    constructor(private userService: UserService,
                private formBuilder: FormBuilder,
                private modalService: NgbModal,
                private dialog: MatDialog,
                private router: Router) {
        this.meetingRequestForm = formBuilder.group({
            title: [''],
            description: [''],
            startDate: [''],
            endDate: [''],
            creatorId: [],
            participantsId: [],
            meetingRoomId: []
        })
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }else {
            this.router.navigate(['']);
        }
        this.loadUserList();
        this.loadMeetingRoomList();

    }

    loadUserList() {
        this.userService.fetchUserListInitData().subscribe(
            (userList) => this.userList = userList,
            error => console.log(error)
        )
    }

    loadMeetingRoomList() {
        this.userService.fetchMeetingRoomListInitData().subscribe(
            (roomList) => {
                this.meetingRoomList = roomList;
                console.log(roomList)
            },
            error => console.log(error)
        )
    }

    clickOnHourSegment(date: Date) {
        this.clickedDate = date;
        const format = "YYYY-MM-DD HH:mm:ss";
        let usingDate = moment(date).format(format).toString();
        this.userService.addStartDate(usingDate);
        // this.userService.clickedDateSubject.next(usingDate);
        console.log(date);
        console.log(usingDate);
        //this.modalService.open(content);

        const dialogRef = this.dialog.open(MeetingDialodComponent);

        dialogRef.afterClosed().subscribe(result => {
        console.log(`Dialog result: ${result}`);
        });
    }

}
