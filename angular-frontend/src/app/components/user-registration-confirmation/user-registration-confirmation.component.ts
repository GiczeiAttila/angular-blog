import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {UserConfirmationModel} from "../../models/userConfirmation.model";
import {handleValidationErrors} from "../../shared/validation.handler";
import {FormBuilder} from "@angular/forms";
import {PasswordModel} from "../../models/password.model";

@Component({
    selector: 'app-user-registration-confirmation',
    templateUrl: './user-registration-confirmation.component.html',
    styleUrls: ['./user-registration-confirmation.component.css']
})
export class UserRegistrationConfirmationComponent implements OnInit {

    confirmation: string;
    id: number;
    token: string;
    hide1: boolean;
    hide2: boolean;
    passwordForm = this.formBuilder.group(
        {
            id: [''],
            token: [''],
            password: [''],
            password2: [''],
        });

    constructor(private activatedRout: ActivatedRoute, private router: Router, private userService: UserService, private formBuilder: FormBuilder) {
        this.activatedRout.paramMap.subscribe(
            map => {
                this.id = +map.get('id');
                this.token = map.get('token');
                if (this.id && !isNaN(this.id) && this.token) {
                    this.passwordForm.patchValue({
                        id: this.id,
                        token: this.token,
                        password: '',
                        password2: ''
                    });
                }
            }
        );
    }

    submitPassword() {
        this.userService.finalizeRegistration(this.passwordForm.value)
            .subscribe(() => {},
                error => {handleValidationErrors(error, this.passwordForm)},
                () => this.router.navigate(['login'])
            );
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }
        this.hide1 = true;
        this.hide2 = true;
    }
}
