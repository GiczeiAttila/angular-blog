import {Component, OnInit} from '@angular/core';
import {CalendarOptions} from '@fullcalendar/angular';

@Component({
    selector: 'app-full-calendar',
    templateUrl: './full-calendar.component.html',
    styleUrls: ['./full-calendar.component.css']
})
export class FullCalendarComponent implements OnInit {

    calendarOptions: CalendarOptions = {
        initialView: 'dayGridMonth',
        weekends: true
    };

    constructor() {
    }

    ngOnInit(): void {
    }

}
