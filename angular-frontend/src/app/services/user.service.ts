import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {UserRegistrationFormModel} from "../models/userRegistrationForm.model";
import {UserConfirmationModel} from "../models/userConfirmation.model";
import {PasswordModel} from "../models/password.model";
import {UserLoginModel} from "../models/userLogin.model";
import {SuggestionFormModel} from "../models/suggestionForm.model";
import {SuggestionListItemModel} from "../models/suggestionListItem.model";
import {SuggestionVoteModel} from "../models/suggestionVote.model";
import {SuggestionStatusChangeModel} from "../models/suggestionStatusChange.model";
import {TimeOffDateRangeDataModel} from "../models/timeOffDateRangeData.model";

const USER_BASE_URL: string = 'http://localhost:8080/api/users';
const SUGGESTION_BASE_URL: string = 'http://localhost:8080/api/suggestions';

@Injectable({providedIn: 'root'})
export class UserService {

    loginSubject: Subject<any> = new Subject<any>();

    constructor(private http: HttpClient) {}


    saveUser(userData: UserRegistrationFormModel) {
        return this.http.post(USER_BASE_URL + '/create', userData);
    }

    savePassword(data: PasswordModel) {
        return this.http.post(USER_BASE_URL + '/password', data);
    }

    finalizeRegistration(confirmationData: UserConfirmationModel) {
        return this.http.post(USER_BASE_URL + '/confirmation', confirmationData);
    }

    login(loginData: UserLoginModel) {
        return this.http.post(USER_BASE_URL + '/login', loginData);
    }

    logout() {
        return this.http.post(USER_BASE_URL + '/logout', null);
    }

    createSuggestion(suggestionData: SuggestionFormModel) {
        console.log(suggestionData);
        return this.http.post(SUGGESTION_BASE_URL, suggestionData);
    }

    getSuggestions(): Observable<Array<SuggestionListItemModel>> {
        return this.http.get<Array<SuggestionListItemModel>>(SUGGESTION_BASE_URL);
    }

    voting(data: SuggestionVoteModel) {
        return this.http.post(SUGGESTION_BASE_URL + '/vote', data);
    }

    changeSuggestionStatus(suggestionStatusChangeData: SuggestionStatusChangeModel) {
        return this.http.put(SUGGESTION_BASE_URL, suggestionStatusChangeData);
    }

    saveTimeOffDateRange(dateData: TimeOffDateRangeDataModel) {
        return this.http.post(USER_BASE_URL + '/timeoff', dateData);
    }
}
