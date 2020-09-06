import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {Moment} from "moment";

@Component({
    selector: 'app-time-off-form',
    templateUrl: './time-off-form.component.html',
    styleUrls: ['./time-off-form.component.css']
})
export class TimeOffFormComponent implements OnInit {

    userId: number;
    timeOffForm: FormGroup;
    selected: { startDate: Moment, endDate: Moment };


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
            error => console.log(error)
        )
    }

}
