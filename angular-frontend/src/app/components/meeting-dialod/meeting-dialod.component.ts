import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {UserForMeetingOptionDtoModel} from "../../models/userForMeetingOptionDto.model";
import {MeetingRoomOptionDtoModel} from "../../models/meetingRoomOptionDto.model";
import {UserService} from "../../services/user.service";
import {Observable} from "rxjs";
import {handleValidationErrors} from "../../shared/validation.handler";

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
    countParticipants = 1;
    selectedUsers: Array<number>;
    filteredOptions: Observable<string[]>;
    options: Array<string> = [];
    userId: number;

    constructor(private userService: UserService,
                private formBuilder: FormBuilder) {
        this.meetingRequestForm = formBuilder.group({
            title: [''],
            description: [''],
            startDate: [''],
            endDate: [''],
            creatorId: [],
            participantsId: FormControl,
            meetingRoomId: []
        })

    }


    ngOnInit(): void {
        this.userId = +localStorage.getItem('userId');

        this.userService.clickedDateSubject.subscribe(
            time => {
                this.startDate = time;
                console.log(this.startDate)
            }
        );
        this.loadUserList();
        this.loadMeetingRoomList();
        /* this.filteredOptions = this.meetingRequestForm.get('participantsId').valueChanges
             .pipe(
                 startWith(''),
                 map(value => this.filter(value))
             );

         */
    }

    loadUserList() {
        this.userService.fetchUserListInitData().subscribe(
            (userList) => {
                this.userList = userList;
                console.log(userList)
            },
            error => console.log(error),
            () => {
                this.userList.forEach((user) => {
                    this.options.unshift(user.userName);
                });
                console.log(this.options)
            }
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
        const meeting = this.meetingRequestForm.value;
        meeting.creatorId = this.userId;
        meeting.participantsId = this.selectedUsers;
        meeting.startDate = this.startDate;

        this.userService.saveNewMeeting(meeting).subscribe(
            () => console.log(meeting),
            error => handleValidationErrors(error, meeting)
        )

    }

    saveUserId(userId: number) {
        this.selectedUsers.unshift(userId);
    }

    private filter(value: string): string[] {
        /* const regex = /\b[A-Z]{2,}\b/g;
         let modified = value.replace(regex, function(match) {
             return match.toLowerCase();
         });

         */
        return this.options.filter(option => option.includes(value));
    }
}
