import {ChangeDetectionStrategy, Component, Injectable, OnInit, ViewEncapsulation} from '@angular/core';
import {UserService} from "../../services/user.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {CalendarEvent, CalendarEventTitleFormatter, CalendarView} from 'angular-calendar';
import {UserForMeetingOptionDtoModel} from "../../models/userForMeetingOptionDto.model";
import {MeetingRoomOptionDtoModel} from "../../models/meetingRoomOptionDto.model";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

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
    meetingFormIsOpen: boolean = false;


    constructor(private userService: UserService,
                private formBuilder: FormBuilder,
                private modalService: NgbModal) {
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


    clickOnHourSegment(date: Date, content) {
        this.clickedDate = date;
        this.modalService.open(content);
    }

    saveMeeting() {

    }
}
