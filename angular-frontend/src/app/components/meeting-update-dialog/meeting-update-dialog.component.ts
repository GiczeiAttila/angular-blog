import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {MeetingReservationDataModel} from "../../models/meetingReservationData.model";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {UserForMeetingOptionDtoModel} from "../../models/userForMeetingOptionDto.model";
import {MeetingRoomOptionDtoModel} from "../../models/meetingRoomOptionDto.model";
import {MeetingReservationUpdatedFormModel} from "../../models/meetingReservationUpdatedForm.model";
import {MatDialogRef} from "@angular/material/dialog";
import * as moment from "moment";

@Component({
    selector: 'app-meeting-update-dialog',
    templateUrl: './meeting-update-dialog.component.html',
    styleUrls: ['./meeting-update-dialog.component.css']
})
export class MeetingUpdateDialogComponent implements OnInit {

    meetingId: number;
    userId: number;
    meetingData: MeetingReservationDataModel;
    meetingUpdateForm: FormGroup;

    defaultUserName: Array<string>;

    userList: UserForMeetingOptionDtoModel[];
    meetingRoomList: MeetingRoomOptionDtoModel[];
    selected;
    minDate;


    constructor(private userService: UserService,
                private formBuilder: FormBuilder,
                private dialogRef: MatDialogRef<MeetingUpdateDialogComponent>) {
        this.meetingUpdateForm = this.formBuilder.group({
            title: [''],
            description: [''],
            startDate: [''],
            startTime: [''],
            endTime: [''],
            creatorId: [],
            participantsId: new FormControl([]),
            meetingRoomId: []
        })
    }

    ngOnInit(): void {
        this.meetingId = this.userService.getMeetingId();
        this.loadActualMeeting();
        const today = new Date()
        const tomorrow = new Date(today)
        tomorrow.setDate(tomorrow.getDate() + 1)
        this.minDate = new Date(tomorrow).toLocaleString("en-CA").substring(0, 10);
    }

    loadActualMeeting() {
        this.userList = [];
        this.defaultUserName = [];
        console.log(this.meetingId);
        this.userService.getMeetingDataToUpdate(this.meetingId).subscribe(
            (data) => {
                this.meetingData = data;
                console.log(data);
                this.meetingUpdateForm.get('title').setValue(data.title);
                this.meetingUpdateForm.get('description').setValue(data.description);
                data.participantsList.forEach(user => {
                    if (user.userId != this.userId) {
                        this.defaultUserName.push(user.userName);
                    }
                })
                this.meetingUpdateForm.get('participantsId').setValue(this.defaultUserName);
                this.meetingUpdateForm.get('meetingRoomId').setValue(data.meetingRoom.roomId);
                this.selected = data.meetingRoom.roomId;

                let startDate = data.startDateTime.substring(0, 10);

                this.meetingUpdateForm.get('startDate').setValue(startDate);
                this.meetingUpdateForm.get('startTime').setValue(new Date(data.startDateTime));
                this.meetingUpdateForm.get('endTime').setValue(new Date(data.endDateTime));

            },
            error => console.log(error),
            () => {
                console.log(this.meetingUpdateForm.value)
            }
        )

        this.userService.fetchMeetingRoomListInitData().subscribe(
            (roomList) => this.meetingRoomList = roomList,
            error => console.log(error)
        )
        this.userId = +localStorage.getItem('userId');
        this.userService.fetchUserListInitData(this.userId).subscribe(
            (userList) => {
                userList.forEach(user => {
                    this.userList.push(user);
                })
            }
        )
    }

    onUserRemoved(user: string) {
        const users = this.meetingUpdateForm.get('participantsId').value as string[];
        this.removeFirst(users, user);
        this.meetingUpdateForm.get('participantsId').setValue(users);
    }

    private removeFirst<T>(array: T[], toRemove: T): void {
        const index = array.indexOf(toRemove);
        if (index !== -1) {
            array.splice(index, 1);
        }
    }

    saveUpdate() {
        this.userId = +localStorage.getItem('userId');
        const format = "YYYY-MM-DD HH:mm";
        let startDate = this.meetingUpdateForm.get('startDate').value;
        let formStartTime = moment(this.meetingUpdateForm.get('startTime').value).format(format);
        let formEndTime = moment(this.meetingUpdateForm.get('endTime').value).format(format);
        let startTime = formStartTime.slice(10);
        let endTime = formEndTime.slice(10);
        let startDateTime = startDate.concat(startTime);
        let endDateTime = startDate.concat(endTime);


        let actualParticipantsId: Array<number> = [];
        let participants: Array<string> = this.meetingUpdateForm.get('participantsId').value;
        participants.forEach(participant => {
            this.userList.forEach(user => {
                if (participant == user.userName) {
                    actualParticipantsId.push(user.userId);
                }
            })
        })
        actualParticipantsId.push(this.userId);

        console.log(this.meetingUpdateForm.value);
        let updatedMeeting: MeetingReservationUpdatedFormModel = {
            meetingId: this.meetingId,
            title: this.meetingUpdateForm.get('title').value,
            description: this.meetingUpdateForm.get('description').value,
            startDateTime: startDateTime,
            endDateTime: endDateTime,
            creatorId: this.userId,
            participantsId: actualParticipantsId,
            meetingRoomId: this.meetingUpdateForm.get('meetingRoomId').value
        }

        this.userService.putUpdatedMeetingData(updatedMeeting).subscribe(
            () => console.log(updatedMeeting),
            error => console.log(error),
            () => this.dialogRef.close()
        )
    }
}
