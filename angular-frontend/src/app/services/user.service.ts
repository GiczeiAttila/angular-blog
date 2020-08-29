import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {UserRegistrationFormModel} from "../models/userRegistrationForm.model";

const BASE_URL: string = 'http://localhost:8080/api/users';

@Injectable({providedIn: 'root'})
export class UserService {

    loginSubject: Subject<any> = new Subject<any>();

    constructor(private http: HttpClient) {}

    authenticate(loginFormData): Observable<any> {
        return this.http.get(BASE_URL + '/me', loginFormData);
    }


    saveUser(userData: UserRegistrationFormModel) {
        return this.http.post(BASE_URL + '/create', userData);
    }
}
