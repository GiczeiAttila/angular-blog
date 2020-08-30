import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {FormBuilder} from "@angular/forms";
import {handleValidationErrors} from "../../shared/validation.handler";
import {PasswordModel} from "../../models/password.model";

@Component({
    selector: 'app-user-password-form',
    templateUrl: './user-password-form.component.html',
    styleUrls: ['./user-password-form.component.css']
})
export class UserPasswordFormComponent implements OnInit {

    id: number;
    passwordForm = this.formBuilder.group(
        {
            password: [''],
            password2: [''],
        });

    constructor(private router: Router, private userService: UserService, private route: ActivatedRoute, private formBuilder: FormBuilder) {
        this.route.paramMap.subscribe(
            map => {
                const idParam = +map.get('id');
                if (idParam && !isNaN(idParam)) {
                    console.log(this.id);
                    this.id = idParam;
                    console.log(this.id);
                }
            },
        );
    }

    ngOnInit(): void {

    }

    submitPassword() {
        console.log(this.passwordForm.value);
        console.log(this.id);
        let data: PasswordModel = {
            'id': this.id,
            'password': this.passwordForm.get('password').value
        };
        console.log(data);

        this.userService.savePassword(data)
            .subscribe(() => {
                },
                error => handleValidationErrors(error, this.passwordForm),
                () => this.router.navigate(['login'])
            );
    }

}
