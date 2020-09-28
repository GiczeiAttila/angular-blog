import {Component, OnInit} from '@angular/core';
import {CalendarOptions} from '@fullcalendar/angular';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";


@Component({
    selector: 'app-fullcalendar',
    templateUrl: './fullcalendar.component.html',
    styleUrls: ['./fullcalendar.component.css']
})
export class FullcalendarComponent implements OnInit {

    userId: number;
    calendarOptions: CalendarOptions;
    allDateList: Array<Object>;


    constructor(private userService: UserService,
                private router: Router) {
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }else {
            this.router.navigate(['']);
        }
        this.userId = +localStorage.getItem('userId');
        this.loadTimeOffList();
    }


    loadTimeOffList() {
        this.allDateList = [];
        this.userService.getTimeOffListForCalendarByUserId(this.userId).subscribe(
            (list) => {
                list.forEach((timeOffData) => {
                    if (timeOffData.title.includes('Accepted')) {
                        timeOffData.color = '#00848C';
                        const acceptedTimeOff: Object = {
                            title: timeOffData.title,
                            start: timeOffData.start,
                            end: timeOffData.end,
                            color: timeOffData.color
                        }
                        this.allDateList.push(acceptedTimeOff);
                    } else if (timeOffData.title.includes('Pending')) {
                        timeOffData.color = '#6D6E71';
                        const pendingTimeOff: Object = {
                            title: timeOffData.title,
                            start: timeOffData.start,
                            end: timeOffData.end,
                            color: timeOffData.color
                        }
                        this.allDateList.push(pendingTimeOff);
                    }
                });
            },
            error => console.log(error),
            () => {
                this.loadMeetingList();
            }
        )
    }

    loadMeetingList() {
        this.userService.getMeetingList(this.userId).subscribe(
            (meetingList) => {
                meetingList.forEach(meeting => {
                    const meetingObject: Object = {
                        title: meeting.title + ' - room: ' + meeting.meetingRoomName,
                        start: meeting.startDate,
                        end: meeting.endDate,
                        color: '#FDA371',
                        description: meeting.description
                    }
                    this.allDateList.push(meetingObject);
                })
            },
            error => console.log(error),
            () => {
                this.loadCalendar();
                console.log(this.allDateList)
            }
        )
    }

    loadCalendar() {
        this.calendarOptions = {
            initialView: 'dayGridMonth',
            timeZone: 'UTC',
            events: this.allDateList
        };
    }


}
