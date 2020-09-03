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

    posts: Array<PostListItemModel>;
    category = 'ALL';
    showAllPost = true;
    commentOpenState: boolean;
    addCommentOpenState: boolean;

    constructor(private blogService: BlogService,
                private userService: UserService,
                private router: Router) {

    }

    ngOnInit() {
        this.loadPosts();
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }
    }

    loadPosts() {
        this.blogService.fetchAllPost().subscribe(
            data => this.posts = data,
            error => console.warn(error)
        );
    }

    changeCategory(category) {
        if (category == 'ALL') {
            this.showAllPost = true;
        } else {
            this.category = category;
            this.showAllPost = false;
        }
    }

    readMore(id: number) {
        this.router.navigate(['posts', id])
    }
}
