import {Component, OnInit} from '@angular/core';
import {BlogService} from '../../services/blog.service';
import {PostListItemModel} from "../../models/postListItem.model";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

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

    constructor(private blogService: BlogService,
                private userService: UserService,
                private router: Router) {
    }

    ngOnInit() {
        this.index = 0;
        this.corporateNews = [];
        this.freeTimeActivities = [];
        this.professionalNews = [];
        this.loadPosts();
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }else {
            this.router.navigate(['']);
        }
    }

    loadPosts() {
        this.blogService.fetchAllPost().subscribe(
            data => {
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
            error => console.warn(error)
        );
    }

    readMore(id: number) {
        this.router.navigate(['posts', id])
    }
}
