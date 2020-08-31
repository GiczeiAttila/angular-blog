import {Component, OnInit} from '@angular/core';
import {handleValidationErrors} from "../../shared/validation.handler";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {HttpClient} from '@angular/common/http';
import {Router} from "@angular/router";

@Component({
    selector: 'app-user-login-form',
    templateUrl: './user-login-form.component.html',
    styleUrls: ['./user-login-form.component.css']
})
export class UserLoginFormComponent implements OnInit {
    loginForm: FormGroup;

    constructor(private userService: UserService, private http: HttpClient, private router: Router, private formBuilder: FormBuilder) {
        this.loginForm = this.formBuilder.group(
            {
                id: ['', Validators.required],
                password: ['', Validators.required]
            });
       /*
        this.loginForm = new FormGroup({
            'id': new FormControl('', Validators.required),
            'password': new FormControl('', Validators.required),
        });*/
    }

    ngOnInit(): void {
    }

    submitLogin() {
        let loginData = this.loginForm.value;
        this.userService.login(loginData)
            .subscribe(() => {localStorage.setItem('auth', 'true')},
                error => {
                console.log(error);
                handleValidationErrors(error, this.loginForm);
                },
                () => {this.router.navigate(['/posts'])},
            );
    }
}
