import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {UserForMeetingOptionDtoModel} from "../../models/userForMeetingOptionDto.model";
import {MeetingRoomOptionDtoModel} from "../../models/meetingRoomOptionDto.model";
import {UserService} from "../../services/user.service";
import {handleValidationErrors} from "../../shared/validation.handler";
import * as moment from "moment";
import {MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {MeetingReservationFormDataModel} from "../../models/meetingReservationFormData.model";


@Component({
    selector: 'app-meeting-dialod',
    templateUrl: './meeting-dialod.component.html',
    styleUrls: ['./meeting-dialod.component.css']
})


export class MeetingDialodComponent implements OnInit {

    meetingRequestForm: FormGroup;
    userList: UserForMeetingOptionDtoModel[];
    meetingRoomList: MeetingRoomOptionDtoModel[];
    startDate;
    actualParticipantsId: Array<number>;
    userId: number;

    constructor(private userService: UserService,
                private formBuilder: FormBuilder,
                private dialogRef: MatDialogRef<MeetingDialodComponent>,
                private router: Router) {
        this.meetingRequestForm = formBuilder.group({
            title: [''],
            description: [''],
            startDateTime: [''],
            endDateTime: [''],
            creatorId: [],
            participantsId: new FormControl([]),
            meetingRoomId: []
        })
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }else {
            this.router.navigate(['']);
        }
        this.startDate = this.userService.getStartDate();
        this.meetingRequestForm.get('startDateTime').setValue(this.startDate);
        this.loadUserList();
        this.loadMeetingRoomList();
    }

    loadUserList() {
        this.userId = +localStorage.getItem('userId');
        this.userService.fetchUserListInitData(this.userId).subscribe(
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
        console.log(this.meetingRequestForm.get('startDateTime').value);

        const meetingForm: MeetingReservationFormDataModel = this.meetingRequestForm.value;
        meetingForm.creatorId = this.userId;

        this.actualParticipantsId = [];
        const participantsLength: number = this.meetingRequestForm.get('participantsId').value.length;
        let actualParticipants = this.meetingRequestForm.get('participantsId').value;
        if (participantsLength > 0) {
            actualParticipants.forEach((participant) => {
                this.userList.forEach(user => {
                    if (participant == user.userName) {
                        this.actualParticipantsId.push(user.userId);
                    }
                })
            })
            meetingForm.participantsId = this.actualParticipantsId;
            meetingForm.participantsId.push(this.userId);
        }
        const format = "YYYY-MM-DD HH:mm";
        const value = this.meetingRequestForm.get('endDateTime').value;
        console.log(value);
        let actualEndDate = moment(value).format(format);
        const startDay = this.meetingRequestForm.get('startDateTime').value.toString().slice(0, 11);
        const endTime = actualEndDate.slice(11);
        const endDate = startDay.concat(endTime);
        meetingForm.endDateTime = endDate;

        this.userService.saveNewMeeting(meetingForm).subscribe(
            () => console.log(meetingForm),
            error => handleValidationErrors(error, this.meetingRequestForm),
            () => {
                this.userService.refreshCalendar.next();
                this.dialogRef.close()
            }
        )
    }

    onUserRemoved(user: string) {
        const users = this.meetingRequestForm.get('participantsId').value as string[];
        this.removeFirst(users, user);
        this.meetingRequestForm.get('participantsId').setValue(users);
    }


    private removeFirst<T>(array: T[], toRemove: T): void {
        const index = array.indexOf(toRemove);
        if (index !== -1) {
            array.splice(index, 1);
        }
    }


}
