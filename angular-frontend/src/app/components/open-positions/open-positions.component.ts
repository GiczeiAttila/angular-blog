import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Moment} from "moment";
import {UserService} from "../../services/user.service";
import {handleValidationErrors} from "../../shared/validation.handler";
import {OpenPositionFormModel} from "../../models/openPositionForm.model";

@Component({
  selector: 'app-open-positions',
  templateUrl: './open-positions.component.html',
  styleUrls: ['./open-positions.component.css']
})
export class OpenPositionsComponent implements OnInit {

    openPositionForm: FormGroup;
    index: number;
    currentUserId: number;
    minDate;
    constructor(private userService: UserService, private formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
            this.currentUserId = +localStorage.getItem("userId");
        }
        this.openPositionForm = this.formBuilder.group(
            {
                positionName: [''],
                description: [''],
                deadline: ['']
            }
        );
        const today = new Date()
        const tomorrow = new Date(today)
        tomorrow.setDate(tomorrow.getDate() + 1)
        this.minDate = new Date(tomorrow).toLocaleString("en-CA").substring(0,10);
        console.log(this.minDate);
        this.index = 0;
    }

    Submit() {
        let openPositionData: OpenPositionFormModel = this.openPositionForm.value;
        openPositionData.userId = +localStorage.getItem('userId')
        this.userService.createOpenPosition(openPositionData)
            .subscribe(() => {
                },
                error => {
                    console.log(error);
                    handleValidationErrors(error, this.openPositionForm);
                },
                () => {
                    this.openPositionForm.reset();
                    this.ngOnInit();
                },
            );
    }
}
