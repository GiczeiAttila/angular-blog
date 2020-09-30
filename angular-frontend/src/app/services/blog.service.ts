import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {CommentFormDataModel} from '../models/commentFormData.model';
import {PostDetailsModel} from '../models/postDetails.model';
import {PostFormDataModel} from '../models/postFormData.model';
import {PostListItemModel} from '../models/postListItem.model';
import {PostFormInitDataModel} from '../models/postFormInitData.model';
import {CommentDetailsModel} from '../models/commentDetails.model';
import {environment} from '../../environments/environment';

const POSTS_BASE_URL = environment.BASE_URL + '/api/posts';
const COMMENTS_BASE_URL = environment.BASE_URL + '/api/comments';

@Injectable({
    providedIn: 'root',
})
export class BlogService {

    refresh = new Subject();

    constructor(private http: HttpClient) {
    }

    fetchPostFormInitData(): Observable<PostFormInitDataModel> {
        return this.http.get<PostFormInitDataModel>(POSTS_BASE_URL + '/formData');
    }

    fetchPosts(): Observable<Array<PostListItemModel>> {
        return this.http.get<Array<PostListItemModel>>(POSTS_BASE_URL);
    }

    createPost(formData: FormData): Observable<any> {
        return this.http.post(POSTS_BASE_URL, formData);
    }

    updatePost(formData: FormData, id: number): Observable<any> {
        return this.http.post(POSTS_BASE_URL + '/' + id, formData);
    }

    fetchPostDetails(id: number): Observable<PostDetailsModel> {
        return this.http.get<PostDetailsModel>(`${POSTS_BASE_URL}/${id}`);
    }

    createComment(commentData: CommentFormDataModel): Observable<any> {
        return this.http.post(COMMENTS_BASE_URL, commentData);
    }

    fetchComments(id: number): Observable<Array<CommentDetailsModel>> {
        return this.http.get<Array<CommentDetailsModel>>(POSTS_BASE_URL);
    }

    fetchAllPost(): Observable<Array<PostListItemModel>> {
        return this.http.get<Array<PostListItemModel>>(POSTS_BASE_URL);
    }

    deletePost(postId: number) {
        return this.http.delete(POSTS_BASE_URL + "/" + postId)
    }

    fetchPostFormData(id: string): Observable<PostFormDataModel> {
        return this.http.get<PostFormDataModel>(`${POSTS_BASE_URL}/formData/${id}`);
    }
}
