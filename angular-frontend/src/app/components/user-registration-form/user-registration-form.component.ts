import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {handleValidationErrors} from "../../shared/validation.handler";
import {UserRegistrationFormModel} from "../../models/userRegistrationForm.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {HelperService} from "../../services/helper.service";
import {UserRegistrationInitModel} from "../../models/userRegistrationInit.model";
import {RoleOptionModel} from "../../models/roleOption.model";

@Component({
    selector: 'app-user-registration-form',
    templateUrl: './user-registration-form.component.html',
    styleUrls: ['./user-registration-form.component.css']
})
export class UserRegistrationFormComponent implements OnInit {

    registrationForm: FormGroup;
    roles: Array<RoleOptionModel>;

    constructor(private formBuilder: FormBuilder,
                private userService: UserService,
                private snackBar: MatSnackBar,
                private helperService: HelperService) {
        this.registrationForm = this.formBuilder.group(
            {
                id: ['',Validators.required],
                firstName: ['',Validators.required],
                lastName: ['',Validators.required],
                email: ['',Validators.required],
                phoneNumber: ['',Validators.required],
                role: ['', Validators.required],
            });
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }
        this.userService.fetchRolesInitData().subscribe(
            (data: UserRegistrationInitModel) => {
                this.roles = data.roles;

            },
            error => console.warn(error),
        );
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
                this.helperService.resetForm(this.registrationForm);
                this.openSnackBar('Registration completed','ok');
            }
            );
    }

    openSnackBar(message: string, action: string) {
        console.log('snackBar');
        this.snackBar.open(message, action, {
            duration: 2000,
            panelClass: ['green-snackbar']
        });
    }
}
