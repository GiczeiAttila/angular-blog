import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {MatSidenav} from "@angular/material/sidenav";
import {HelperService} from "../../services/helper.service";

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {

    isLoggedIn: boolean;
    userId: string = '';
    timer;
    actualTime: Date;
    actualMinutes: string;
    actualSeconds: string;
    temperature: string;
    humidity: string;
    location: string;
    weather: string;
    wind: string;





    constructor(private userService: UserService, private router: Router,private helperService: HelperService) {
        this.userService.loginSubject.subscribe(() => {
            this.isLoggedIn = localStorage.getItem('auth') ? true : false;
            this.userId = localStorage.getItem('userId');
        })
        this.timer = setInterval(()=>{this.actualTime=new Date;
            if (this.actualTime.getMinutes() < 10) {
                this.actualMinutes="0"+this.actualTime.getMinutes();
            }else{
                this.actualMinutes=''+ this.actualTime.getMinutes();
            }
            if (this.actualTime.getSeconds() < 10) {
                this.actualSeconds="0"+this.actualTime.getSeconds();
            }else{
                this.actualSeconds=''+ this.actualTime.getSeconds();
            }},1000);

    }

    ngOnInit(){
        if (localStorage.getItem('auth')) {
            this.isLoggedIn = true;
            this.userId= localStorage.getItem('userId')
        } else {
            this.isLoggedIn = false;
        }
        this.helperService.getCurrentWeather('Budapest').subscribe((res) => {
            console.log(res);
            this.location = res['name'];
            this.temperature = res['main'].temp +'°C';
            this.humidity=  res['main'].humidity+'%';
            this.weather= res['weather'][0].description;
            this.wind =  res['wind'].speed+'km/h';
        });
    }

    logout() {
        this.userService.logout().subscribe(() => {
                localStorage.clear();
                this.userId = '';
                this.isLoggedIn = localStorage.getItem('auth') ? true : false;
            }
        );

    }

    togleInTs(sidenavMaster: MatSidenav,sidenavSlave: MatSidenav) {
        sidenavMaster.toggle();
        sidenavMaster.autoFocus=false;
        sidenavSlave.autoFocus=false;
        sidenavSlave.opened=false;
    }




    ngOnDestroy(): void {
        this.timer.clearInterval();
    }
}
