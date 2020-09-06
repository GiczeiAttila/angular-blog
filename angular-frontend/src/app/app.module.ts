import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {PostListComponent} from './components/post-list/post-list.component';
import {PostFormComponent} from './components/post-form/post-form.component';
import {PostDetailsComponent} from './components/post-details/post-details.component';
import {CommentFormComponent} from './components/comment-form/comment-form.component';
import {UserRegistrationFormComponent} from './components/user-registration-form/user-registration-form.component';
import {UserLoginFormComponent} from './components/user-login-form/user-login-form.component';
import {WelcomeComponent} from './components/welcome/welcome.component';
import {UserRegistrationConfirmationComponent} from './components/user-registration-confirmation/user-registration-confirmation.component';
import {UserPasswordFormComponent} from './components/user-password-form/user-password-form.component';
import {CommentDetailsComponent} from "./components/comment-details/comment-details.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {SuggestionBoxComponent} from './components/suggestion-box/suggestion-box.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {MaterialModule} from "./material-modul";
import {BsDatepickerModule} from "ngx-bootstrap/datepicker";

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        PostListComponent,
        PostFormComponent,
        PostDetailsComponent,
        CommentFormComponent,
        UserRegistrationFormComponent,
        UserLoginFormComponent,
        WelcomeComponent,
        UserRegistrationConfirmationComponent,
        UserPasswordFormComponent,
        CommentDetailsComponent,
        PageNotFoundComponent,
        SuggestionBoxComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        MaterialModule,
        BsDatepickerModule.forRoot()
    ],
    entryComponents: [AppComponent],
    providers: [{ provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'fill' }}],
    bootstrap: [AppComponent]
})
export class AppModule {
}
