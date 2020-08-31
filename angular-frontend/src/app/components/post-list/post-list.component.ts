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

  constructor(private blogService: BlogService) { }

  ngOnInit() {
      this.loadPosts();
  }

    loadPosts() {
        this.blogService.fetchPosts().subscribe(
            data => this.posts = data,
            error => console.warn(error)
        );
    }

}
