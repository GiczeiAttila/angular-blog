import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PostFormComponent} from './components/post-form/post-form.component';
import {PostListComponent} from './components/post-list/post-list.component';
import {PostDetailsComponent} from './components/post-details/post-details.component';
import {UserLoginFormComponent} from "./components/user-login-form/user-login-form.component";
import {UserRegistrationFormComponent} from "./components/user-registration-form/user-registration-form.component";
import {WelcomeComponent} from "./components/welcome/welcome.component";
import {UserPasswordFormComponent} from "./components/user-password-form/user-password-form.component";
import {UserRegistrationConfirmationComponent} from "./components/user-registration-confirmation/user-registration-confirmation.component";

import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {SuggestionBoxComponent} from "./components/suggestion-box/suggestion-box.component";
import {TimeOffFormComponent} from "./components/time-off-form/time-off-form.component";
import {TimeOffListComponent} from "./components/time-off-list/time-off-list.component";
import {OpenPositionsComponent} from "./components/open-positions/open-positions.component";
import {FullcalendarComponent} from "./components/fullcalendar/fullcalendar.component";
import {MeetingRoomFormComponent} from "./components/meeting-room-form/meeting-room-form.component";
import {MeetingReservationFormComponent} from "./components/meeting-reservation-form/meeting-reservation-form.component";
import {MeetingListComponent} from "./components/meeting-list/meeting-list.component";


const routes: Routes = [
    {path: 'welcome', component: WelcomeComponent},
    {path: 'posts', component: PostListComponent},
    {path: 'registration', component: UserRegistrationFormComponent},
    {path: 'login', component: UserLoginFormComponent},
    {path: 'posts/:id', component: PostDetailsComponent},
    {path: 'confirmation/:token/:id', component: UserRegistrationConfirmationComponent},
    {path: 'password/:id', component: UserPasswordFormComponent},
    {path: 'postForm', component: PostFormComponent},
    {path: 'postForm/:id', component: PostFormComponent},
    {path: 'suggestion-box', component: SuggestionBoxComponent},
    {path: 'open-position', component: OpenPositionsComponent},
    {path: 'timeOffForm', component: TimeOffFormComponent},
    {path: 'timeOff-list', component: TimeOffListComponent},
    {path: 'fullcalendar', component: FullcalendarComponent},
    {path: 'meetingRoom-form', component: MeetingRoomFormComponent},
    {path: 'meetingReservation', component: MeetingReservationFormComponent},
    {path: 'meeting-list', component: MeetingListComponent},
    {path: '', redirectTo: 'welcome', pathMatch: 'full'},
    {path: '**', component: PageNotFoundComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
