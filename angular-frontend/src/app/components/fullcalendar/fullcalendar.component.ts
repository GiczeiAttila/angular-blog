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
    calendarOptions: CalendarOptions;


    constructor(private userService: UserService) {
    }

    ngOnInit(): void {
        this.userId = +localStorage.getItem('userId');
        this.loadTimeOffList();
        this.loadCalendar()
    }

    handleDateClick(arg) {
        alert('date click! ' + arg.dateStr)

    }

    loadTimeOffList() {
        this.userService.getTimeOffListForCalendarByUserId(this.userId).subscribe(
            (list: CalendarTimeOffListDtoModel[]) => this.timeOffList = list,
            error => console.log(error),
            () => this.loadCalendar()
        )
    }

    loadCalendar() {
        this.calendarOptions = {
            initialView: 'dayGridMonth',
            dateClick: this.handleDateClick.bind(this),
            timeZone: 'UTC',
            events: this.timeOffList,

        };
    }

}
