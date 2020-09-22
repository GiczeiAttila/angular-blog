import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {handleValidationErrors} from "../../shared/validation.handler";
import {OpenPositionFormModel} from "../../models/openPositionForm.model";
import {OpenPositionListItemModel} from "../../models/openPositionListItem.model";
import {MatTabChangeEvent} from "@angular/material/tabs";
import {ApplicationForOpenPositionModel} from "../../models/applicationForOpenPosition.model";

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
    activeOpenPositionList: Array<OpenPositionListItemModel>;
    myOpenPositionList: Array<OpenPositionListItemModel>;

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
        this.myOpenPositionList = [];
        this.activeOpenPositionList = [];
        this.userService.getActiveOpenPositions(this.currentUserId).subscribe((response) => {
            console.log(response);
            this.activeOpenPositionList = response;
        });
    }

    Submit() {
        let openPositionData: OpenPositionFormModel = this.openPositionForm.value;
        openPositionData.userId = +localStorage.getItem('userId')
        this.userService.createOpenPosition(openPositionData)
            .subscribe(() => {
                },
                error => {
                    handleValidationErrors(error, this.openPositionForm);
                },
                () => {
                    this.ngOnInit();
                },
            );
    }

    tabChanged(tabChangeEvent: MatTabChangeEvent): void {
        console.log('tabChangeEvent => ', tabChangeEvent);
        console.log('index => ', tabChangeEvent.index);
        if (tabChangeEvent.index===1){
            this.getMyOpenPositions();
        }else if (tabChangeEvent.index===0){
            this.ngOnInit();
        }
    }

    apply(openPositionId: number) {
        let applicationForOpenPositionModel: ApplicationForOpenPositionModel = {
            applicantId: this.currentUserId,
            openPositionId: openPositionId
        }
        this.userService.applyToOpenPosition(applicationForOpenPositionModel).subscribe(()=>{},
            ()=>{},
            ()=>{this.ngOnInit()});
    }

    applicantList(id: number) {

    }

    getMyOpenPositions() {
        this.userService.getMyOpenPositions(this.currentUserId).subscribe((response) => {
            console.log(response);
            this.myOpenPositionList = response});
    }
}
