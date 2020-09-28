import {MeetingParticipantListItem} from "./meetingParticipantListItem";

export interface UserMeetingReservationListItemModel {

    meetingId: number;
    title: string;
    description: string;
    startDate: string;
    endDate: string;
    creatorId: number;
    participants: Array<MeetingParticipantListItem>;
    meetingRoomId: number;
    meetingRoomName: string;
    status: string;
}
