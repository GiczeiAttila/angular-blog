import {CategoryOptionModel} from "./categoryOption.model";
import {TypeOptionModel} from "./typeOption.model";

export interface PostFormInitDataModel {

    categories: Array<CategoryOptionModel>;
    types: Array<TypeOptionModel>;
}
