import {AddressFormDataModel} from "./addressFormData.model";

export interface PostFormDataModel {
    title: string;
    postBody: string;
    picture?: string;
    category: string;
    type: string;
    address: AddressFormDataModel;
}
