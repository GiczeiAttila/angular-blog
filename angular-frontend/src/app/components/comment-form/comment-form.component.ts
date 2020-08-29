import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BlogService } from '../../services/blog.service';
import { handleValidationErrors } from '../../shared/validation.handler';
import {UserService} from "../../services/user.service";

@Component({
    selector: 'app-comment-form',
    templateUrl: './comment-form.component.html',
    styleUrls: ['./comment-form.component.css'],
})
export class CommentFormComponent implements OnInit {

    @Input() postId: number;
    @Output() commentCreated = new EventEmitter();
    commentForm: FormGroup;

    constructor(private formBuilder: FormBuilder, private blogService: BlogService, private userService: UserService) {
        this.commentForm = formBuilder.group({
            author: [''],
            commentBody: ['', Validators.required]
            }
        )
    }

    ngOnInit() {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }
    }

    onSubmit() {
        const commentData = {...this.commentForm.value};
        commentData.author = commentData.author == null ? '' : commentData.author;
        commentData.postId = this.postId;
        this.blogService.createComment(commentData).subscribe(
            () => {
                this.commentForm.reset();
                this.commentCreated.emit();
            },
            error => handleValidationErrors(error, this.commentForm)
        )
    }
}
