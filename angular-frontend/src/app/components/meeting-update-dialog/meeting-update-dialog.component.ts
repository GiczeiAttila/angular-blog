import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {MeetingReservationDataModel} from "../../models/meetingReservationData.model";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {UserForMeetingOptionDtoModel} from "../../models/userForMeetingOptionDto.model";
import {MeetingRoomOptionDtoModel} from "../../models/meetingRoomOptionDto.model";

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
    startTime: string;
    time = {hour: 13, minute: 30};

    constructor(private userService: UserService,
                private formBuilder: FormBuilder) {
        this.meetingUpdateForm = this.formBuilder.group({
            title: [''],
            description: [''],
            startDate: [''],
            startTime: [''],
            endDate: [''],
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
                let endDate = data.endDateTime.substring(0, 10);
                this.startTime = data.startDateTime.substring(11).toString();
                let endTime = data.endDateTime.substring(11);
                console.log(this.startTime);

                this.meetingUpdateForm.get('startDate').setValue(startDate);
                this.meetingUpdateForm.get('startTime').setValue('10:20:00');
                this.meetingUpdateForm.get('endDate').setValue(endDate);
                // this.meetingUpdateForm.get('endTime').setValue(endTime);

            },
            error => console.log(error),
            () => {
                console.log("Get data");
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

    }
}
