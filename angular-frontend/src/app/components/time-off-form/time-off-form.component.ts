import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {UserTimeOffListModel} from "../../models/userTimeOffList.model";
import {handleValidationErrors} from "../../shared/validation.handler";
import {TimeOffDateRangeDataModel} from "../../models/timeOffDateRangeData.model";
import {MatDatepickerInputEvent} from '@angular/material/datepicker';


@Component({
    selector: 'app-time-off-form',
    templateUrl: './time-off-form.component.html',
    styleUrls: ['./time-off-form.component.css']
})
export class TimeOffFormComponent implements OnInit {

    @Output()
    dateChange: EventEmitter<MatDatepickerInputEvent<Date>>


    userId: number;
    timeOffForm: FormGroup;
    minDate;
    minDateForEndDate;
    //selected: { startDate: Moment, endDate: Moment };
    acceptedTimeOffList: Array<UserTimeOffListModel>;
    rejectedTimeOffList: Array<UserTimeOffListModel>;
    pendingTimeOffList: Array<UserTimeOffListModel>;
    index: number;


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
        const today = new Date()
        const tomorrow = new Date(today)
        tomorrow.setDate(tomorrow.getDate() + 1)
        this.minDate = new Date(tomorrow).toLocaleString("en-CA").substring(0, 10);
        this.minDateForEndDate = new Date(tomorrow).toLocaleString("en-CA").substring(0, 10);
        this.acceptedTimeOffList = [];
        this.rejectedTimeOffList = [];
        this.pendingTimeOffList = [];
        this.loadTimeOffList();
        this.startDateChanged();
    }

    saveDate() {
        /*  this.selected.startDate.format('yyyy-MM-DD');
          this.selected.endDate.format('yyyy-MM-DD');
          const form = {
              userId: this.userId,
              startDate: this.selected.startDate.format('yyyy-MM-DD'),
              endDate: this.selected.endDate.format('yyyy-MM-DD')
          }

         */


        let actualForm: TimeOffDateRangeDataModel = this.timeOffForm.value;
        actualForm.userId = this.userId;
        this.userService.saveTimeOffDateRange(actualForm).subscribe(
            () => {
                console.log(actualForm)
            },
            error => {
                console.log(error);
                handleValidationErrors(error, this.timeOffForm)
            }
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
