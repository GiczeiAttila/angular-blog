import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../services/user.service";
import {UserConfirmationModel} from "../../models/userConfirmation.model";
import {handleValidationErrors} from "../../shared/validation.handler";

@Component({
  selector: 'app-user-registration-confirmation',
  templateUrl: './user-registration-confirmation.component.html',
  styleUrls: ['./user-registration-confirmation.component.css']
})
export class UserRegistrationConfirmationComponent implements OnInit {


    confirmation: string;
    id: number;
    token: string;
    userConfirmationData: UserConfirmationModel;

    constructor(private activatedRout: ActivatedRoute, private router: Router, private userService: UserService) {
      this.activatedRout.paramMap.subscribe(
          map => {
              const idParam = +map.get('id');
              if (idParam && !isNaN(idParam)) {
                  this.id = idParam;
                  this.token = map.get('token');
                  this.userConfirmationData.id = this.id;
                  this.userConfirmationData.token = this.token;

                  this.userService.sendConfirmation(this.userConfirmationData).subscribe(
                      ()=>{},
                      error =>console.log(error),
                      ()=>{
                          this.confirmation='Registration confirmed!';
                          router.navigate(['/password', this.id])
                      }
                  )
              }
          },
      );
  }

  ngOnInit(): void {

  }

}
