interface PostDetailsModel {
    id: number;
    title: string;
    postBody: string;
    imgUrl: string;
    createdAt: string;
    comments: Array<CommentDetailsModel>;
}
