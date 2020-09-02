import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {handleValidationErrors} from "../../shared/validation.handler";
import {SuggestionFormModel} from "../../models/suggestionForm.model";
import {SuggestionListItemModel} from "../../models/SuggestionListItem.model";

@Component({
    selector: 'app-suggestion-box',
    templateUrl: './suggestion-box.component.html',
    styleUrls: ['./suggestion-box.component.css']
})
export class SuggestionBoxComponent implements OnInit {
    suggestionForm: FormGroup;
    activeIndex: number = 0;
    panelOpenState: boolean;
    mySuggestionList: Array<SuggestionListItemModel>;
    suggestionList: Array<SuggestionListItemModel>;

    constructor(private userService: UserService, private http: HttpClient, private router: Router, private formBuilder: FormBuilder) {
        this.suggestionForm = this.formBuilder.group(
            {
                title: ['', Validators.required],
                description: ['', Validators.required]
            }
        );
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }
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
                    this.activeIndex = 0;
                },
            );
    }

    upVote() {

    }

    downVote() {

    }
}
