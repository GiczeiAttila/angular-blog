import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {handleValidationErrors} from "../../shared/validation.handler";
import {UserRegistrationFormModel} from "../../models/userRegistrationForm.model";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
    selector: 'app-user-registration-form',
    templateUrl: './user-registration-form.component.html',
    styleUrls: ['./user-registration-form.component.css']
})
export class UserRegistrationFormComponent implements OnInit {

    registrationForm: FormGroup;

    constructor(private formBuilder: FormBuilder,
                private userService: UserService,
                private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }
        this.registrationForm = this.formBuilder.group(
            {
                id: ['',Validators.required],
                firstName: ['',Validators.required],
                lastName: ['',Validators.required],
                email: ['',Validators.required],
                phoneNumber: ['',Validators.required],
            });
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
                this.resetForm(this.registrationForm);
                this.openSnackBar('Registration completed','ok');
            }
            );
    }

    openSnackBar(message: string, action: string) {
        console.log('snackBar');
        this.snackBar.open(message, action, {
            duration: 500,
            panelClass: ['green-snackbar']
        });
    }

    resetForm(form: FormGroup) {

        form.reset();

        Object.keys(form.controls).forEach(key => {
            form.get(key).setErrors(null) ;
        });
    }

}
