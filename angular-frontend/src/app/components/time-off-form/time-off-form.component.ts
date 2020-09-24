import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {UserTimeOffListModel} from "../../models/userTimeOffList.model";
import {handleValidationErrors} from "../../shared/validation.handler";
import {TimeOffDateRangeDataModel} from "../../models/timeOffDateRangeData.model";
import {Router} from "@angular/router";


@Component({
    selector: 'app-time-off-form',
    templateUrl: './time-off-form.component.html',
    styleUrls: ['./time-off-form.component.css']
})
export class TimeOffFormComponent implements OnInit {

    userId: number;
    timeOffForm: FormGroup;
    minDate;
    minDateForEndDate;
    acceptedTimeOffList: Array<UserTimeOffListModel>;
    rejectedTimeOffList: Array<UserTimeOffListModel>;
    pendingTimeOffList: Array<UserTimeOffListModel>;
    index: number;

    constructor(private formBuilder: FormBuilder,
                private userService: UserService,
                private router: Router) {

        this.createForm();
    }

    ngOnInit() {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }else {
            this.router.navigate(['']);
        }
        this.userId = +localStorage.getItem('userId');
        this.index = 0;
        const today = new Date()
        const tomorrow = new Date(today)
        tomorrow.setDate(tomorrow.getDate() + 1)
        this.minDate = new Date(tomorrow).toLocaleString("en-CA").substring(0, 10);
        this.minDateForEndDate = new Date(tomorrow).toLocaleString("en-CA").substring(0, 10);
        this.loadTimeOffList();
        this.startDateChanged();
    }

    createForm() {
        this.timeOffForm = this.formBuilder.group({
            userId: [],
            startDate: [''],
            endDate: ['']
        });
    }

    saveDate() {
        let actualForm: TimeOffDateRangeDataModel = this.timeOffForm.value;
        actualForm.userId = this.userId;
        this.userService.saveTimeOffDateRange(actualForm).subscribe(
            () => {
                console.log(actualForm)
            },
            error => {
                console.log(error);
                handleValidationErrors(error, this.timeOffForm)
            },
            () => {
                this.pendingTimeOffList = [];
                this.timeOffForm.reset();
                this.ngOnInit();
                this.index = 2;
            }
        )
    }

    loadTimeOffList() {
        this.userService.getUserTimeOffList(this.userId).subscribe(
            (data) => {
                this.acceptedTimeOffList = [];
                this.rejectedTimeOffList = [];
                this.pendingTimeOffList = [];
                data.forEach((timeOffListModel) => {
                    if (timeOffListModel.status == 'ACCEPTED') {
                        this.acceptedTimeOffList.unshift(timeOffListModel);
                    } else if (timeOffListModel.status == 'PENDING') {
                        this.pendingTimeOffList.unshift(timeOffListModel);
                    } else if (timeOffListModel.status == 'REJECTED') {
                        this.rejectedTimeOffList.unshift(timeOffListModel);
                    }
                })
            },
            error => console.log(error)
        )
    }

    startDateChanged() {
        this.timeOffForm.get('startDate').valueChanges.subscribe(
            (oldValue) => {
                console.log(oldValue + 'changed');
                setTimeout(() => {
                    this.minDateForEndDate = this.timeOffForm.get('startDate').value;
                });
                this.ngOnInit();
            }
        )
    }


}
