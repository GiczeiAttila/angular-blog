import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserForMeetingOptionDtoModel} from "../../models/userForMeetingOptionDto.model";
import {MeetingRoomOptionDtoModel} from "../../models/meetingRoomOptionDto.model";
import {UserService} from "../../services/user.service";
import {Observable} from "rxjs";
import {map, startWith} from 'rxjs/operators';

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
    selectedUsers: UserForMeetingOptionDtoModel[];
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
            participantsId: [],
            meetingRoomId: []
        })

    }

    ngOnInit(): void {
        this.userId = +localStorage.getItem('userId');

        this.userService.clickedDateSubject.subscribe(
            time => this.startDate = time
        )
        this.loadUserList();
        this.loadMeetingRoomList();
        this.filteredOptions = this.meetingRequestForm.valueChanges
            .pipe(
                startWith(''),
                map(value => this.filter(value))
            );
    }

    loadUserList() {
        this.userService.fetchUserListInitData().subscribe(
            (userList) => {
                this.userList = userList;
                console.log(userList)
            },
            error => console.log(error)
        )
        this.userList.forEach((user) => {
            this.options.unshift(user.userName);
        })
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

    }

    private filter(value: string): string[] {
        const filterValue = value.toLowerCase();
        return this.options.filter(option => option.toLowerCase().includes(filterValue));
    }
}
