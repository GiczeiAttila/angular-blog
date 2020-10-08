import {Component, OnInit} from '@angular/core';
import {BlogService} from '../../services/blog.service';
import {PostListItemModel} from "../../models/postListItem.model";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {CommentDetailsModel} from "../../models/commentDetails.model";

@Component({
    selector: 'app-post-list',
    templateUrl: './post-list.component.html',
    styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {

    allPost: Array<PostListItemModel>;
    corporateNews: Array<PostListItemModel>;
    freeTimeActivities: Array<PostListItemModel>;
    professionalNews: Array<PostListItemModel>;

    commentOpenState: boolean;
    addCommentOpenState: boolean;
    index: number;
    userId: number;
    isUnderEditing: boolean;
    editedCommentId: number;

    constructor(private blogService: BlogService,
                private userService: UserService,
                private router: Router) {
    }

    ngOnInit() {
        this.loadPosts();
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
            this.userId = +localStorage.getItem('userId');
        }else {
            this.router.navigate(['']);
        }
    }

    loadPosts() {
        this.index = 0;
        this.corporateNews = [];
        this.freeTimeActivities = [];
        this.professionalNews = [];
        this.blogService.fetchAllPost().subscribe(
            data => {
                console.log(data);
                this.allPost = data;
                data.forEach((post) => {
                    if (post.category == 'CORPORATE') {
                        this.corporateNews.push(post);
                    } else if (post.category == 'FREE_TIME_ACTIVITIES') {
                        this.freeTimeActivities.push(post);
                    } else {
                        this.professionalNews.push(post);
                    }
                })
            },
            error => console.warn(error),
            () => {
                this.isUnderEditing = false;
                this.editedCommentId = undefined;
            }
        );
    }

    readMore(id: number) {
        this.router.navigate(['posts', id])
    }

    delete(postId: number) {
        this.blogService.deletePost(postId).subscribe(
            () => {
            },
            () => {
            },
            () => {
                this.loadPosts()
            }
        );
    }

    edit(id: number) {
        this.router.navigate(['postForm', id])
    }

    editComment(comment: CommentDetailsModel) {
        this.editedCommentId = comment.id;
        this.isUnderEditing = true;
    }
}
