export interface PostListItemModel {
    id: number;
    title: string;
    postBodyShortened: string;
    picture?: string;
    createdAt: string;
    numberOfComments: number;
}
