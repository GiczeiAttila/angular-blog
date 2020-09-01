import {Component, OnInit} from '@angular/core';
import {BlogService} from '../../services/blog.service';
import {PostListItemModel} from "../../models/postListItem.model";

@Component({
    selector: 'app-post-list',
    templateUrl: './post-list.component.html',
    styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {

    posts: Array<PostListItemModel>;
    category = 'ALL';
    showAllPost = true;
    postIdForComments: number;
    commentStatus: boolean = false;


    constructor(private blogService: BlogService) {
    }

    ngOnInit() {
        this.loadPosts();
    }

    loadPosts() {
        this.blogService.fetchPosts().subscribe(
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

    showCommentsById(id: number) {
        this.postIdForComments = id;
        this.clickOnComments();
    }

    clickOnComments() {
        this.commentStatus = !this.commentStatus;
    }


}
