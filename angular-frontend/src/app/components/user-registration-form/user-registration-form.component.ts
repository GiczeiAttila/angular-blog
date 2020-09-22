import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {handleValidationErrors} from "../../shared/validation.handler";
import {UserRegistrationFormModel} from "../../models/userRegistrationForm.model";

@Component({
    selector: 'app-user-registration-form',
    templateUrl: './user-registration-form.component.html',
    styleUrls: ['./user-registration-form.component.css']
})
export class UserRegistrationFormComponent implements OnInit {

    registrationForm = this.formBuilder.group(
        {
            id: ['',Validators.required],
            firstName: ['',Validators.required],
            lastName: ['',Validators.required],
            email: ['',Validators.required],
            phoneNumber: ['',Validators.required],
        });

    constructor(private formBuilder: FormBuilder,
                private userService: UserService) {
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }
    }

    submitRegistration() {
        console.log(this.registrationForm.value);
        let userData: UserRegistrationFormModel = {...this.registrationForm.value};
        console.log(userData);

        this.userService.saveUser(userData)
            .subscribe(() => {
                },
                error => handleValidationErrors(error, this.registrationForm),
                () => {this.registrationForm.reset()}
            );
    }

}
