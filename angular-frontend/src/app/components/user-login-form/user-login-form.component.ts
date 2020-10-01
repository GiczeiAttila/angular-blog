import {Component, OnInit} from '@angular/core';
import {handleValidationErrors} from "../../shared/validation.handler";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-user-login-form',
    templateUrl: './user-login-form.component.html',
    styleUrls: ['./user-login-form.component.css']
})
export class UserLoginFormComponent implements OnInit {
    loginForm: FormGroup;
    hide: boolean;

    constructor(private userService: UserService, private router: Router, private formBuilder: FormBuilder) {
        this.loginForm = this.formBuilder.group(
            {
                id: ['', Validators.required],
                password: ['', Validators.required]
            }
        );
    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
            this.router.navigate(['/posts']);
        }

        this.hide = true;
    }

    submitLogin() {
        let loginData = this.loginForm.value;
        this.userService.login(loginData)
            .subscribe(() => {
                },
                error => {
                    console.log(error);
                    handleValidationErrors(error, this.loginForm);
                },
                () => {
                    localStorage.setItem('auth', 'true');
                    localStorage.setItem('userId', loginData.id);
                    this.userService.loginSubject.next();
                    this.router.navigate(['/posts']);
                },
            );
    }

    // toggleShowPassword(){
    //     this.hide= !this.hide;
    // }
}
