import {Component, OnInit} from '@angular/core';
import {handleValidationErrors} from "../../shared/validation.handler";
import {FormControl, FormGroup, Validators} from "@angular/forms";
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

    constructor(private userService: UserService, private http: HttpClient, private router: Router) {
        this.loginForm = new FormGroup({
            'id': new FormControl('', Validators.required),
            'password': new FormControl('', Validators.required),
        });

    }

    ngOnInit() {
    }

    onSubmit() {
        const data = {...this.loginForm.value};
        console.log(data);

        this.userService.authenticate(data).subscribe(
            response => {
                localStorage.setItem('auth', 'true');
                localStorage.setItem('id', JSON.stringify(response));
                this.router.navigate(['/posts']);
            },
            error => {
                error.error = {
                    fieldErrors: [
                        {
                            field: 'id',
                            message: 'Invalid username or password',
                        },
                    ],
                };
                handleValidationErrors(error, this.loginForm);
            });

        return false;
    }


}
