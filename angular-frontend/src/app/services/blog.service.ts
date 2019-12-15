import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const POSTS_BASE_URL = 'http://localhost:8080/api/posts';
const COMMENTS_BASE_URL = 'http://localhost:8080/api/comments';

@Injectable({
  providedIn: 'root'
})
export class BlogService {

    constructor(private http: HttpClient) {}

    fetchPosts(): Observable<Array<PostListItemModel>> {
        return this.http.get<Array<PostListItemModel>>(POSTS_BASE_URL)
    }

    createPost(postData: PostFormDataModel): Observable<any> {
        return this.http.post(POSTS_BASE_URL, postData);
    }

    fetchPostDetails(id: number): Observable<PostDetailsModel> {
        return this.http.get<PostDetailsModel>(`${POSTS_BASE_URL}/${id}`)
    }

    createComment(commentData: CommentFormDataModel): Observable<any> {
        return this.http.post(COMMENTS_BASE_URL, commentData);
    }
}
