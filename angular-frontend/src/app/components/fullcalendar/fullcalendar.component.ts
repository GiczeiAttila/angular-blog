import {Component, OnInit} from '@angular/core';
import {CalendarOptions} from '@fullcalendar/angular';
import {UserService} from "../../services/user.service";
import {CalendarTimeOffListDtoModel} from "../../models/calendarTimeOffListDto.model";

@Component({
    selector: 'app-fullcalendar',
    templateUrl: './fullcalendar.component.html',
    styleUrls: ['./fullcalendar.component.css']
})
export class FullcalendarComponent implements OnInit {

    userId: number;
    timeOffList: CalendarTimeOffListDtoModel[];

    calendarOptions: CalendarOptions = {
        initialView: 'dayGridMonth',
        dateClick: this.handleDateClick.bind(this),
        timeZone: 'UTC',

        //events: [this.timeOffList],
        events: [{
            title: 'lalalala',
            start: '2020-09-10',
            end: '2020-09-14'
        }]
    };


    constructor(private userService: UserService) {
    }

    ngOnInit(): void {
        this.userId = +localStorage.getItem('userId');
        this.loadTimeOffEvents();
    }

    handleDateClick(arg) {
        alert('date click! ' + arg.dateStr)
    }

    loadTimeOffEvents() {
        this.userService.getTimeOffListForCalendarByUserId(this.userId).subscribe(
            (list: CalendarTimeOffListDtoModel[]) => this.timeOffList = list,
            error => console.log(error),
            () => console.log(this.timeOffList)
        )
    }


}
