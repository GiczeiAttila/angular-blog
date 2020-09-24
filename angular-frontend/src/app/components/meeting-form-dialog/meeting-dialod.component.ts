import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {UserForMeetingOptionDtoModel} from "../../models/userForMeetingOptionDto.model";
import {MeetingRoomOptionDtoModel} from "../../models/meetingRoomOptionDto.model";
import {UserService} from "../../services/user.service";
import {handleValidationErrors} from "../../shared/validation.handler";
import * as moment from "moment";
import {Router} from "@angular/router";


@Component({
    selector: 'app-meeting-dialod',
    templateUrl: './meeting-dialod.component.html',
    styleUrls: ['./meeting-dialod.component.css']
})


export class MeetingDialodComponent implements OnInit {

    meetingRequestForm: FormGroup;
    participantsIdControl = new FormControl([]);

    userList: UserForMeetingOptionDtoModel[];
    meetingRoomList: MeetingRoomOptionDtoModel[];
    startDate;
    participantsId: Array<number> = [];
    countParticipants = 1;
    time = {hour: 13, minute: 30};

    userId: number;

    constructor(private userService: UserService,
                private formBuilder: FormBuilder,
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
        // this.userService.clickedDateSubject.subscribe(
        //     time => {
        //         this.startDate = time;
        //         this.meetingRequestForm.get('startDate').setValue(time);
        //         console.log(this.startDate);
        //     }
        // );
        this.startDate = this.userService.getStartDate();
        this.meetingRequestForm.get('startDate').setValue(this.startDate);
        this.loadUserList();
        this.loadMeetingRoomList();
    }

    loadUserList() {
        this.userService.fetchUserListInitData().subscribe(
            (userList) => {
                this.userList = userList;
            },
            error => console.log(error)
        )
    }

    loadMeetingRoomList() {
        this.userService.fetchMeetingRoomListInitData().subscribe(
            (roomList) => {
                this.meetingRoomList = roomList;
            },
            error => console.log(error)
        )
    }

    saveMeeting() {
        this.userId = +localStorage.getItem('userId');
        const meetingForm = this.meetingRequestForm.value;
        meetingForm.creatorId = this.userId;
        console.log(this.meetingRequestForm.value);
        console.log(this.startDate);

        this.participantsId = [];
        this.participantsIdControl.value.forEach(participant => {
            this.userList.forEach(user => {
                if (participant == user.userName) {
                    this.participantsId.push(user.userId);
                }
            })
            console.log(this.participantsId);
        })
        meetingForm.participantsId = this.participantsId;

        const format = "YYYY-MM-DD HH:mm:ss";
        const value = this.meetingRequestForm.get('endDate').value;
        let actualEndDate = moment(value).format(format);
        meetingForm.endDate = actualEndDate;
        console.log(actualEndDate);


        // const actualDay = this.startDate.substring(0, 12);
        // const actualEndTime = actualEndDate.substring(12);
        //const allEndDate = actualDay.concat(actualEndTime);
        //meetingForm.endDate = allEndDate;
        //meetingForm.startDate = this.startDate;


        //meetingForm.startDate='2020-09-20 10:00:00';
        //meetingForm.endDate='2020-09-20 11:00:00';

        this.userService.saveNewMeeting(meetingForm).subscribe(
            () => console.log(meetingForm),
            error => handleValidationErrors(error, meetingForm)
        )

    }

    onUserRemoved(user: string) {
        const users = this.participantsIdControl.value as string[];
        this.removeFirst(users, user);
        this.participantsIdControl.setValue(users);
    }

    private removeFirst<T>(array: T[], toRemove: T): void {
        const index = array.indexOf(toRemove);
        if (index !== -1) {
            array.splice(index, 1);
        }
    }
}
