import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {Moment} from "moment";
import {UserTimeOffListModel} from "../../models/UserTimeOffList.model";
import {handleValidationErrors} from "../../shared/validation.handler";

@Component({
    selector: 'app-time-off-form',
    templateUrl: './time-off-form.component.html',
    styleUrls: ['./time-off-form.component.css']
})
export class TimeOffFormComponent implements OnInit {

    userId: number;
    timeOffForm: FormGroup;
    selected: { startDate: Moment, endDate: Moment };
    acceptedTimeOffList: Array<UserTimeOffListModel>;
    rejectedTimeOffList: Array<UserTimeOffListModel>;
    pendingTimeOffList: Array<UserTimeOffListModel>;
    index: number;
    displayedColumns: any[] = ['startDate', 'endDate'];


    constructor(private formBuilder: FormBuilder,
                private userService: UserService) {
        this.timeOffForm = this.formBuilder.group({
            userId: [],
            startDate: [''],
            endDate: ['']
        });
    }

    ngOnInit() {
        this.userId = +localStorage.getItem('userId');
        this.index = 0;
        this.acceptedTimeOffList = [];
        this.rejectedTimeOffList = [];
        this.pendingTimeOffList = [];
        this.loadTimeOffList();
    }

    saveDate() {
        this.selected.startDate.format('yyyy-MM-DD');
        this.selected.endDate.format('yyyy-MM-DD');
        const form = {
            userId: this.userId,
            startDate: this.selected.startDate.format('yyyy-MM-DD'),
            endDate: this.selected.endDate.format('yyyy-MM-DD')
        }

        this.userService.saveTimeOffDateRange(form).subscribe(
            () => {
                console.log(form)
            },
            error => handleValidationErrors(error, this.timeOffForm)
        )
    }

    loadTimeOffList() {
        this.userService.getUserTimeOffList(this.userId).subscribe(
            (data) => {
                data.forEach((timeOffListModel) => {
                    if (timeOffListModel.status == 'ACCEPTED') {
                        this.acceptedTimeOffList.unshift(timeOffListModel);
                    } else if (timeOffListModel.status == 'PENDING') {
                        this.pendingTimeOffList.unshift(timeOffListModel);
                    } else {
                        this.rejectedTimeOffList.unshift(timeOffListModel);
                    }
                })
            },
            error => console.log(error)
        )
    }

}
