export interface MeetingReservationFormDataModel {

    title: string;
    description: string;
    startDateTime: string;
    endDateTime: string;
    creatorId: number;
    participantsId: Array<number>;
    meetingRoomId: number;
}
