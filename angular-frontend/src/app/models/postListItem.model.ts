import {CommentDetailsModel} from "./commentDetails.model";

export interface PostListItemModel {
    id: number;
    author: string;
    title: string;
    postBodyShortened: string;
    picture?: string;
    createdAt: string;
    numberOfComments: number;
    category: string;
    type: string;
    comments: Array<CommentDetailsModel>;
}
