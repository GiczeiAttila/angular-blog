import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {TimeOffListItemModel} from "../../models/timeOffListItem.model";
import {TimeOffStatusChangeDto} from "../../models/timeOffStatusChangeDto";
import {Router} from "@angular/router";

@Component({
    selector: 'app-time-off-list',
    templateUrl: './time-off-list.component.html',
    styleUrls: ['./time-off-list.component.css']
})
export class TimeOffListComponent implements OnInit {

    acceptedTimeOffList: Array<TimeOffListItemModel>;
    rejectedTimeOffList: Array<TimeOffListItemModel>;
    pendingTimeOffList: Array<TimeOffListItemModel>;
    index: number;
    currentUserId: number
    displayedColumns: string[] = ['Id', 'User name', 'Start date', 'End date', 'Status'];

    constructor(private userService: UserService,
                private router: Router) {
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
            this.currentUserId = +localStorage.getItem("userId");
        }else {
            this.router.navigate(['']);
        }
        this.index = 0;
        this.acceptedTimeOffList = [];
        this.rejectedTimeOffList = [];
        this.pendingTimeOffList = [];
        this.loadTimeOffList();

        this.currentUserId = +localStorage.getItem('userId');
    }

    acceptRequest(dateId: number) {
        this.changeTimeOffRequestStatus('ACCEPTED', dateId);

    }

    rejectRequest(dateId: number) {
        this.changeTimeOffRequestStatus('REJECTED', dateId);
    }

    changeToPending(dateId: number) {
        this.changeTimeOffRequestStatus('PENDING', dateId);
    }

    changeTimeOffRequestStatus(newStatus: string, id: number) {
        let statusChangeData: TimeOffStatusChangeDto = {
            dateId: id,
            status: newStatus
        };

        this.userService.changeTimeOffRequestStatus(statusChangeData).subscribe(
            () => this.ngOnInit()
        );
    }

    private loadTimeOffList() {

        this.userService.getAllTimeOffList().subscribe(
            (data) => {
                data.forEach((timeOffListItemModel) => {
                    if (timeOffListItemModel.status == 'ACCEPTED') {
                        this.acceptedTimeOffList.unshift(timeOffListItemModel);
                    } else if (timeOffListItemModel.status == 'PENDING') {
                        this.pendingTimeOffList.unshift(timeOffListItemModel);
                    } else if (timeOffListItemModel.status == 'REJECTED') {
                        this.rejectedTimeOffList.unshift(timeOffListItemModel);
                    }
                })
            },
            error => console.log(error)
        )
    }

}
