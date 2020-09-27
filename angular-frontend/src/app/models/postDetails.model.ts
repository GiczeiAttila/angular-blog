import {CommentDetailsModel} from "./commentDetails.model";
import {AddressDetailsModel} from "./addressDetails.model";

export interface PostDetailsModel {
    id: number;
    author: string;
    title: string;
    postBody: string;
    picture: object;
    createdAt: string;
    comments: Array<CommentDetailsModel>;
    category: string;
    type: string;
    address: AddressDetailsModel;

}
