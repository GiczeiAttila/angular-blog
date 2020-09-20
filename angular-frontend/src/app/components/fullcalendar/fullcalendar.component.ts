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
    timeOffList: Array<CalendarTimeOffListDtoModel> = [];
    calendarOptions: CalendarOptions;
    calendarEvents = [];


    constructor(private userService: UserService) {
    }

    ngOnInit(): void {
        this.userId = +localStorage.getItem('userId');
        this.loadTimeOffList();
        //this.loadCalendar();
    }

    handleDateClick(arg) {

    }

    loadTimeOffList() {
        this.userService.getTimeOffListForCalendarByUserId(this.userId).subscribe(
            (list) => {
                list.forEach((timeOffData) => {
                    if (timeOffData.title.includes('Accepted')) {
                        timeOffData.color = '#00848C';
                        this.timeOffList.unshift(timeOffData);
                    } else if (timeOffData.title.includes('Pending')) {
                        timeOffData.color = '#6D6E71';
                        this.timeOffList.unshift(timeOffData);
                    }
                });
                console.log(this.timeOffList)
            },
            error => console.log(error),
            () => {
                this.calendarOptions = {
                    initialView: 'dayGridMonth',
                    dateClick: this.handleDateClick.bind(this),
                    timeZone: 'UTC',
                    events: this.timeOffList,
                };

            }
        )
    }

    /* loadCalendar() {
         this.calendarOptions = {
             initialView: 'dayGridMonth',
             dateClick: this.handleDateClick.bind(this),
             timeZone: 'UTC',
             events: this.timeOffList,
         };
     }

     */


}
