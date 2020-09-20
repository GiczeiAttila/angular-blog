export interface MeetingReservationFormDataModel {

    title: string;
    description: string;
    startDate: string;
    endDate: string;
    creatorId: number;
    participantsId: Array<number>;
    meetingRoomId: number;
}
