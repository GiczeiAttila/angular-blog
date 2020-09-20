import {Component, OnInit} from '@angular/core';
import {CalendarOptions} from '@fullcalendar/angular';
import {UserService} from "../../services/user.service";
import {CalendarAcceptedTimeOffListDtoModel} from "../../models/calendarAcceptedTimeOffListDto.model";
import {CalendarPendingTimeOffListDtoModel} from "../../models/calendarPendingTimeOffListDto.model";


@Component({
    selector: 'app-fullcalendar',
    templateUrl: './fullcalendar.component.html',
    styleUrls: ['./fullcalendar.component.css']
})
export class FullcalendarComponent implements OnInit {

    userId: number;
    //timeOffList = [];
    acceptedTimeOffList: CalendarAcceptedTimeOffListDtoModel[];
    pendingTimeOffList: CalendarAcceptedTimeOffListDtoModel[];
    calendarOptions: CalendarOptions;
    calendarEvents = [];


    constructor(private userService: UserService) {
    }

    ngOnInit(): void {
        this.userId = +localStorage.getItem('userId');
        this.loadTimeOffList();
        this.loadCalendar();
    }

    handleDateClick(arg) {
        alert('date click! ' + arg.dateStr)

    }

    loadTimeOffList() {
        this.userService.getAcceptedTimeOffListForCalendarByUserId(this.userId).subscribe(
            (list: CalendarAcceptedTimeOffListDtoModel[]) => {
                list.forEach(timeOffData => {
                    if (timeOffData.title.includes("Accepted")) {

                    }
                })
            },
            error => console.log(error),
            () => this.loadCalendar()
        )

        this.userService.getPendingTimeOffListForCalendarByUserId(this.userId).subscribe(
            (list: CalendarPendingTimeOffListDtoModel[]) => {
                this.pendingTimeOffList = list;
            },
            error => console.log(error)
        )

        this.concatEvents();
    }

    loadCalendar() {
        this.calendarOptions = {
            initialView: 'dayGridMonth',
            dateClick: this.handleDateClick.bind(this),
            timeZone: 'UTC',
            events: this.calendarEvents,
        };
    }

    concatEvents() {
        this.calendarEvents = this.acceptedTimeOffList;
        this.calendarEvents = this.calendarEvents.concat(this.pendingTimeOffList);
    }

}
