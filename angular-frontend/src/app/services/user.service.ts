import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {UserRegistrationFormModel} from "../models/userRegistrationForm.model";
import {UserConfirmationModel} from "../models/userConfirmation.model";
import {PasswordModel} from "../models/password.model";
import {UserLoginModel} from "../models/userLogin.model";
import {SuggestionFormModel} from "../models/suggestionForm.model";

const BASE_URL: string = 'http://localhost:8080/api/users';

@Injectable({providedIn: 'root'})
export class UserService {

    loginSubject: Subject<any> = new Subject<any>();

    constructor(private http: HttpClient) {}


    saveUser(userData: UserRegistrationFormModel) {
        return this.http.post(BASE_URL + '/create', userData);
    }

    savePassword(data: PasswordModel) {
        return this.http.post(BASE_URL + '/password', data);
    }

    finalizeRegistration(confirmationData: UserConfirmationModel) {
        return this.http.post(BASE_URL + '/confirmation', confirmationData);
    }

    login(loginData: UserLoginModel) {
        return this.http.post(BASE_URL + '/login', loginData);
    }

    logout() {
        return this.http.post(BASE_URL + '/logout', null);
    }

    createSuggestion(suggestionData: SuggestionFormModel) {
        console.log(suggestionData);
        return this.http.post(BASE_URL + '/suggestion',suggestionData);
    }
}
