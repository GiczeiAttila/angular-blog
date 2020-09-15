import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {TimeOffListItemModel} from "../../models/timeOffListItem.model";
import {TimeOffStatusChangeDto} from "../../models/timeOffStatusChangeDto";

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

    constructor(private userService: UserService) {
    }

    ngOnInit(): void {
        this.index = 0;
        this.acceptedTimeOffList = [];
        this.rejectedTimeOffList = [];
        this.pendingTimeOffList = [];
        this.loadTimeOffList();
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
                    } else {
                        this.rejectedTimeOffList.unshift(timeOffListItemModel);
                    }
                })
            },
            error => console.log(error)
        )
    }

}
