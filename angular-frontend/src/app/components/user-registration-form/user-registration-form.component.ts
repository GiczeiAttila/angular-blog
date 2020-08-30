import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
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
            id: [''],
            firstName: [''],
            lastName: [''],
            email: [''],
            phoneNumber: [''],
            password: [''],
            password2: [''],
        });

    constructor(private formBuilder: FormBuilder,
                private userService: UserService) {
    }

    ngOnInit(): void {
    }

    submitRegistration() {
        console.log(this.registrationForm.value);
        let userData: UserRegistrationFormModel = {...this.registrationForm.value};
        console.log(userData);

        this.userService.saveUser(userData)
            .subscribe(() => {
                },
                error => handleValidationErrors(error, this.registrationForm),
                () => {

                }
            );
    }

}
