import { Injectable } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";


const URL = 'https://api.openweathermap.org/data/2.5/weather?q=';
const APIkey = 'f681ff7de513fe2743b71e7355113a0c';

@Injectable({
    providedIn: 'root'
})
export class HelperService {



  constructor(private http: HttpClient) { }

    resetForm(form: FormGroup) {

        form.reset();

        Object.keys(form.controls).forEach(key => {
            form.get(key).setErrors(null) ;
        });
    }

    getCurrentWeather(city) {
        return this.http.get(URL + city + '&units=metric' + '&APPID=' + APIkey);
    }



}
