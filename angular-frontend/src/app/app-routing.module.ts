import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PostFormComponent } from './components/post-form/post-form.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { PostDetailsComponent } from './components/post-details/post-details.component';
import {UserLoginFormComponent} from "./components/user-login-form/user-login-form.component";
import {UserRegistrationFormComponent} from "./components/user-registration-form/user-registration-form.component";
import {WelcomeComponent} from "./components/welcome/welcome.component";
import {UserPasswordFormComponent} from "./components/user-password-form/user-password-form.component";
import {UserRegistrationConfirmationComponent} from "./components/user-registration-confirmation/user-registration-confirmation.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";


const routes: Routes = [
    {path: 'welcome', component: WelcomeComponent},
    {path: 'posts', component: PostListComponent},
    {path: 'registration', component: UserRegistrationFormComponent},
    {path: 'login', component: UserLoginFormComponent},
    {path: 'posts/:id', component: PostDetailsComponent},
    {path: 'confirmation/:token/:id', component: UserRegistrationConfirmationComponent},
    {path: 'password/:id', component: UserPasswordFormComponent},
    {path: 'postForm', component: PostFormComponent},
    {path: '**', component: PageNotFoundComponent},
    {path: '', redirectTo: 'welcome', pathMatch: 'full'},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
