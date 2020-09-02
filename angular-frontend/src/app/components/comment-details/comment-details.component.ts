import {Component, Input, OnInit} from '@angular/core';
import {BlogService} from "../../services/blog.service";
import {CommentDetailsModel} from "../../models/commentDetails.model";

@Component({
    selector: 'app-comment-details',
    templateUrl: './comment-details.component.html',
    styleUrls: ['./comment-details.component.css']
})
export class CommentDetailsComponent implements OnInit {

    postComments: Array<CommentDetailsModel>;
    @Input()
    postId: number;

    constructor(private blogService: BlogService) {
    }

    ngOnInit(): void {
        this.blogService.fetchComments(this.postId).subscribe(
            (comments: CommentDetailsModel[]) => this.postComments = comments,
            error => console.log(error),
            () => console.log(this.postId)
        )
    }

}
