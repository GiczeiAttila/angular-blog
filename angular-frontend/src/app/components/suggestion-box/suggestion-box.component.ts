import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {handleValidationErrors} from "../../shared/validation.handler";
import {SuggestionFormModel} from "../../models/suggestionForm.model";
import {SuggestionListItemModel} from "../../models/suggestionListItem.model";
import {SuggestionVoteModel} from "../../models/suggestionVote.model";

@Component({
    selector: 'app-suggestion-box',
    templateUrl: './suggestion-box.component.html',
    styleUrls: ['./suggestion-box.component.css']
})
export class SuggestionBoxComponent implements OnInit {
    suggestionForm: FormGroup;
    index: number;
    panelOpenState: boolean;
    mySuggestionList: Array<SuggestionListItemModel> = [];
    activeSuggestionList: Array<SuggestionListItemModel> = [];
    closedSuggestionList: Array<SuggestionListItemModel> = [];

    constructor(private userService: UserService, private http: HttpClient, private router: Router, private formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }
        this.suggestionForm = this.formBuilder.group(
            {
                title: ['', Validators.required],
                description: ['', Validators.required]
            }
        );
        this.index = 0;
        this.userService.getSuggestions().subscribe((response) => {
            console.log(response);
            response.forEach((suggestionListItemModel) => {
                if (suggestionListItemModel.userId === +localStorage.getItem('userId')) {
                    this.mySuggestionList.unshift(suggestionListItemModel);
                    if (suggestionListItemModel.status == 'ACTIVE') {
                        this.activeSuggestionList.unshift(suggestionListItemModel);
                    } else {
                        this.closedSuggestionList.unshift(suggestionListItemModel);
                    }
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
        this.userService.voting(data).subscribe();
    }
}
