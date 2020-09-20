import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {UserRegistrationFormModel} from '../models/userRegistrationForm.model';
import {UserConfirmationModel} from '../models/userConfirmation.model';
import {PasswordModel} from '../models/password.model';
import {UserLoginModel} from '../models/userLogin.model';
import {SuggestionFormModel} from '../models/suggestionForm.model';
import {SuggestionListItemModel} from '../models/suggestionListItem.model';
import {SuggestionVoteModel} from '../models/suggestionVote.model';
import {SuggestionStatusChangeModel} from '../models/suggestionStatusChange.model';
import {TimeOffDateRangeDataModel} from '../models/timeOffDateRangeData.model';
import {environment} from '../../environments/environment';
import {UserTimeOffListModel} from "../models/userTimeOffList.model";
import {TimeOffListItemModel} from "../models/timeOffListItem.model";
import {TimeOffStatusChangeDto} from "../models/timeOffStatusChangeDto";
import {OpenPositionFormModel} from "../models/openPositionForm.model";
import {OpenPositionListItemModel} from "../models/openPositionListItem.model";
import {ApplicationForOpenPositionModel} from "../models/applicationForOpenPosition.model";
import {CalendarAcceptedTimeOffListDtoModel} from "../models/calendarAcceptedTimeOffListDto.model";
import {MeetingRoomFormDataModel} from "../models/meetingRoomFormData.model";
import {MeetingReservationFormDataModel} from "../models/meetingReservationFormData.model";
import {MeetingRoomOptionDtoModel} from "../models/meetingRoomOptionDto.model";
import {UserForMeetingOptionDtoModel} from "../models/userForMeetingOptionDto.model";
import {CalendarPendingTimeOffListDtoModel} from "../models/calendarPendingTimeOffListDto.model";

const USER_BASE_URL: string = environment.BASE_URL + '/api/users';
const SUGGESTION_BASE_URL: string = environment.BASE_URL + '/api/suggestions';
const REQUEST_BASE_URL: string = environment.BASE_URL + '/api/requests'
const OPEN_POSITION_URL: string = environment.BASE_URL + '/api/open-positions';
const MEETING_URL: string = environment.BASE_URL + '/api/meetings';

@Injectable({providedIn: 'root'})
export class UserService {

    loginSubject: Subject<any> = new Subject<any>();

    constructor(private http: HttpClient) {
    }

    saveUser(userData: UserRegistrationFormModel) {
        return this.http.post(USER_BASE_URL + '/create', userData);
    }

    savePassword(data: PasswordModel) {
        return this.http.post(USER_BASE_URL + '/password', data);
    }

    finalizeRegistration(confirmationData: UserConfirmationModel) {
        return this.http.post(USER_BASE_URL + '/confirmation', confirmationData);
    }

    login(loginData: UserLoginModel) {
        return this.http.post(USER_BASE_URL + '/login', loginData);
    }

    logout() {
        return this.http.post(USER_BASE_URL + '/logout', null);
    }

    createSuggestion(suggestionData: SuggestionFormModel) {
        console.log(suggestionData);
        return this.http.post(SUGGESTION_BASE_URL, suggestionData);
    }

    getSuggestions(): Observable<Array<SuggestionListItemModel>> {
        return this.http.get<Array<SuggestionListItemModel>>(SUGGESTION_BASE_URL);
    }

    voting(data: SuggestionVoteModel) {
        return this.http.post(SUGGESTION_BASE_URL + '/vote', data);
    }

    changeSuggestionStatus(suggestionStatusChangeData: SuggestionStatusChangeModel) {
        return this.http.put(SUGGESTION_BASE_URL, suggestionStatusChangeData);
    }

    saveTimeOffDateRange(dateData: TimeOffDateRangeDataModel) {
        return this.http.post(REQUEST_BASE_URL + '/timeOffForm', dateData);
    }

    getUserTimeOffList(id: number): Observable<Array<UserTimeOffListModel>> {
        return this.http.get<Array<UserTimeOffListModel>>(REQUEST_BASE_URL + '/timeOff/' + id);
    }

    getAllTimeOffList(): Observable<Array<TimeOffListItemModel>> {
        return this.http.get<Array<TimeOffListItemModel>>(REQUEST_BASE_URL + '/timeOff/all');
    }

    changeTimeOffRequestStatus(timeOffStatusChangeDto: TimeOffStatusChangeDto) {
        return this.http.put(REQUEST_BASE_URL, timeOffStatusChangeDto);
    }

    createOpenPosition(openPositionData: OpenPositionFormModel) {
        console.log(openPositionData);
        return this.http.post(OPEN_POSITION_URL, openPositionData);
    }

    getActiveOpenPositions(currentUserId: number): Observable<Array<OpenPositionListItemModel>> {
        return this.http.get<Array<OpenPositionListItemModel>>(OPEN_POSITION_URL + '/open-positions/' + currentUserId);
    }

    getMyOpenPositions(currentUserId: number): Observable<Array<OpenPositionListItemModel>> {
        return this.http.get<Array<OpenPositionListItemModel>>(OPEN_POSITION_URL + '/my-open-positions/' + currentUserId);
    }

    /*  getMyApplications(currentUserId: number): Observable<Array<OpenPositionListItemModel>> {
          return this.http.get<Array<OpenPositionListItemModel>>(OPEN_POSITION_URL + '/my-applications/' + currentUserId);
      }
  */
    applyToOpenPosition(applicationForOpenPositionModel: ApplicationForOpenPositionModel) {
        console.log(applicationForOpenPositionModel);
        return this.http.post(OPEN_POSITION_URL + '/apply', applicationForOpenPositionModel);
    }

    getAcceptedTimeOffListForCalendarByUserId(id: number): Observable<Array<CalendarAcceptedTimeOffListDtoModel>> {
        return this.http.get<Array<CalendarAcceptedTimeOffListDtoModel>>(REQUEST_BASE_URL + '/calendarAccepted/' + id);
    }

    getPendingTimeOffListForCalendarByUserId(id: number): Observable<Array<CalendarAcceptedTimeOffListDtoModel>> {
        return this.http.get<Array<CalendarPendingTimeOffListDtoModel>>(REQUEST_BASE_URL + '/calendarPending/' + id);
    }

    createMeetingRoom(meetingRoomFormData: MeetingRoomFormDataModel) {
        return this.http.post(MEETING_URL + '/createRoom', meetingRoomFormData);
    }

    saveNewMeeting(meetingReservationForm: MeetingReservationFormDataModel) {
        return this.http.post(MEETING_URL + '/addMeeting', meetingReservationForm);
    }

    fetchMeetingRoomListInitData(): Observable<Array<MeetingRoomOptionDtoModel>> {
        return this.http.get<Array<MeetingRoomOptionDtoModel>>(MEETING_URL + '/meetingRoom');
    }

    fetchUserListInitData(): Observable<Array<UserForMeetingOptionDtoModel>> {
        return this.http.get<Array<UserForMeetingOptionDtoModel>>(USER_BASE_URL);
    }
}
