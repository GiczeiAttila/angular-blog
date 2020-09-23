import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {handleValidationErrors} from "../../shared/validation.handler";
import {SuggestionFormModel} from "../../models/suggestionForm.model";
import {SuggestionListItemModel} from "../../models/suggestionListItem.model";
import {SuggestionVoteModel} from "../../models/suggestionVote.model";
import {SuggestionStatusChangeModel} from "../../models/suggestionStatusChange.model";
import {HelperService} from "../../services/helper.service";

@Component({
    selector: 'app-suggestion-box',
    templateUrl: './suggestion-box.component.html',
    styleUrls: ['./suggestion-box.component.css']
})
export class SuggestionBoxComponent implements OnInit {
    suggestionForm: FormGroup;
    index: number;
    panelOpenState: boolean;
    mySuggestionList: Array<SuggestionListItemModel>;
    activeSuggestionList: Array<SuggestionListItemModel>;
    closedSuggestionList: Array<SuggestionListItemModel>;
    currentUserId: number;

    constructor(private userService: UserService,
                private http: HttpClient,
                private router: Router,
                private formBuilder: FormBuilder,
                private helperService: HelperService) {
    }

    ngOnInit(): void {
        this.mySuggestionList = [];
        this.activeSuggestionList = [];
        this.closedSuggestionList = [];
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
            this.currentUserId = +localStorage.getItem("userId");
        }
        this.suggestionForm = this.formBuilder.group(
            {
                title: [''],
                description: ['']
            }
        );
        this.index = 0;
        this.userService.getSuggestions().subscribe((response) => {
            console.log(response);
            response.forEach((suggestionListItemModel) => {

                if (suggestionListItemModel.userId === +localStorage.getItem('userId')) {
                    this.mySuggestionList.unshift(suggestionListItemModel);
                }
                if (suggestionListItemModel.status == 'ACTIVE') {
                    this.activeSuggestionList.unshift(suggestionListItemModel);
                } else {
                    this.closedSuggestionList.unshift(suggestionListItemModel);
                }
            })
        });
    }

    Submit() {
        let suggestionData: SuggestionFormModel = this.suggestionForm.value;
        suggestionData.userId = +localStorage.getItem('userId')
        this.userService.createSuggestion(suggestionData)
            .subscribe(() => {
                },
                error => {
                    console.log(error);
                    handleValidationErrors(error, this.suggestionForm);
                },
                () => {
                    this.helperService.resetForm(this.suggestionForm);
                    this.ngOnInit();
                },
            );
    }

    voting(vote: string, userId: number, id: number) {
        let data: SuggestionVoteModel = {
            suggestionId: id,
            userId: userId,
            vote: vote,
            votingUserId: +localStorage.getItem('userId')
        }
        this.userService.voting(data).subscribe(
            ()=>{},
            ()=>{},
            ()=>{this.ngOnInit()}
        );
    }

    reactivateSuggestion(suggestionId: number) {
        this.changeSuggestionStatus('ACTIVE', suggestionId)
    }

    rejectSuggestion(suggestionId: number) {
        this.changeSuggestionStatus('REJECTED', suggestionId)
    }

    acceptSuggestion(suggestionId: number) {
        this.changeSuggestionStatus('ACCEPTED', suggestionId)
    }

    changeSuggestionStatus(status: string, suggestionId: number){
        let suggestionStatusChangeData: SuggestionStatusChangeModel = {
            status: status,
            currentUserId: this.currentUserId,
            suggestionId: suggestionId
        };
        this.userService.changeSuggestionStatus(suggestionStatusChangeData).subscribe(()=>{
            this.ngOnInit();
        });
    }
}
