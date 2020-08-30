import {CommentDetailsModel} from "./commentDetails.model";

export interface PostDetailsModel {
    id: number;
    title: string;
    postBody: string;
    picture: string;
    createdAt: string;
    comments: Array<CommentDetailsModel>;
}
