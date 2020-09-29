import {MeetingRoomOptionDtoModel} from "./meetingRoomOptionDto.model";
import {MeetingParticipantListItem} from "./meetingParticipantListItem";

export interface MeetingReservationDataModel {

    title: string;
    description: string;
    startDateTime: string;
    creatorId: number;
    endDateTime: string;
    participantsList: Array<MeetingParticipantListItem>;
    meetingRoom: MeetingRoomOptionDtoModel;
}
