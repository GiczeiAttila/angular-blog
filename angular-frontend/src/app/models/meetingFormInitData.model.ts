import {MeetingRoomOptionDtoModel} from "./meetingRoomOptionDto.model";
import {UserForMeetingOptionDtoModel} from "./userForMeetingOptionDto.model";

export interface MeetingFormInitDataModel {

    meetingRooms: Array<MeetingRoomOptionDtoModel>;
    users: Array<UserForMeetingOptionDtoModel>;
}
