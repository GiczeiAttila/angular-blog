import {AddressFormDataModel} from "./addressFormData.model";

export interface PostFormDataModel {
    authorId: number;
    title: string;
    postBody: string;
    picture?: string;
    category: string;
    type: string;
    address: AddressFormDataModel;
}
