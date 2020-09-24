import {Component, Input, OnInit} from '@angular/core';
import {BlogService} from "../../services/blog.service";
import {CommentDetailsModel} from "../../models/commentDetails.model";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-comment-details',
    templateUrl: './comment-details.component.html',
    styleUrls: ['./comment-details.component.css']
})
export class CommentDetailsComponent implements OnInit {

    postComments: Array<CommentDetailsModel>;
    @Input()
    postId: number;

    constructor(private blogService: BlogService,private router: Router, private userService: UserService) {

    }

    ngOnInit(): void {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }else {
            this.router.navigate(['']);
        }
        this.blogService.fetchComments(this.postId).subscribe(
            (comments: CommentDetailsModel[]) => this.postComments = comments,
            error => console.log(error),
            () => console.log(this.postId)
        )
    }

}
