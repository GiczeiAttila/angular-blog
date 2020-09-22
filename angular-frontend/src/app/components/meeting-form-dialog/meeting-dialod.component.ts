import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {UserForMeetingOptionDtoModel} from "../../models/userForMeetingOptionDto.model";
import {MeetingRoomOptionDtoModel} from "../../models/meetingRoomOptionDto.model";
import {UserService} from "../../services/user.service";
import {handleValidationErrors} from "../../shared/validation.handler";
import * as moment from "moment";


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
    startDateString: string;
    participantsId: Array<number> = [];
    countParticipants = 1;
    time = {hour: 13, minute: 30};

    userId: number;

    constructor(private userService: UserService,
                private formBuilder: FormBuilder) {
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
        this.userService.clickedDateSubject.subscribe(
            time => {
                this.startDate = time;
                console.log(this.startDate)
                this.startDateString = this.startDate.toString();
            }
        );
        this.loadUserList();
        this.loadMeetingRoomList();
    }

    loadUserList() {
        this.userService.fetchUserListInitData().subscribe(
            (userList) => {
                this.userList = userList;
                console.log(userList)
            },
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

    saveMeeting() {
        this.userId = +localStorage.getItem('userId');
        const meeting = this.meetingRequestForm.value;
        meeting.creatorId = this.userId;
        meeting.startDate = this.startDate;
        console.log(this.startDate);
        this.participantsId = [];
        this.participantsIdControl.value.forEach(participant => {
            this.userList.forEach(user => {
                if (participant == user.userName) {
                    this.participantsId.unshift(user.userId);
                }
            })
            console.log(this.participantsId);
        })
        meeting.participantsId = this.participantsId;


        const format = "YYYY-MM-DD HH:mm:ss";
        const value = this.meetingRequestForm.get('endData').value;

        console.log(value);
        console.log(this.startDate);

        let actualEndDate = moment(value).format(format);
        const actualDay = this.startDate.substring(0, 12);
        const actualEndTime = actualEndDate.substring(12);
        const allEndDate = actualDay.concat(actualEndTime);
        meeting.endDate = allEndDate;
        meeting.startDate = this.startDate;


        //meeting.startDate='2020-09-20 10:00:00';
        //meeting.endDate='2020-09-20 11:00:00';

        this.userService.saveNewMeeting(meeting).subscribe(
            () => console.log(meeting),
            error => handleValidationErrors(error, meeting)
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
