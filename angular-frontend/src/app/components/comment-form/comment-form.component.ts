import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BlogService} from '../../services/blog.service';
import {handleValidationErrors} from '../../shared/validation.handler';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {CommentFormDataModel} from "../../models/commentFormData.model";
import {CommentDetailsModel} from "../../models/commentDetails.model";

@Component({
    selector: 'app-comment-form',
    templateUrl: './comment-form.component.html',
    styleUrls: ['./comment-form.component.css'],
})
export class CommentFormComponent implements OnInit {

    @Input() postId: number;
    authorId: number;
    @Output() commentCreated = new EventEmitter();
    commentForm: FormGroup;
    @Input() comment: CommentDetailsModel;

    constructor(private formBuilder: FormBuilder,
                private router: Router, private blogService: BlogService, private userService: UserService) {
        this.commentForm = formBuilder.group({
                commentBody: ['', Validators.required]
            }
        )
    }

    ngOnInit() {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        } else {
            this.router.navigate(['']);
        }
        console.log(this.comment);
        if (this.comment) {
            this.commentForm.get('commentBody').setValue(this.comment?.commentBody);
        }

        this.authorId = +localStorage.getItem('userId');
    }

    onSubmit() {
        if (this.comment) {
            this.update(this.comment);
        } else {
            this.save();
        }
    }

    cancel() {
        this.commentCreated.emit();
    }

    private save() {
        const commentData = {...this.commentForm.value};
        console.log(commentData);
        commentData.postId = this.postId;
        commentData.authorId = this.authorId;
        this.blogService.createComment(commentData).subscribe(
            () => {
                this.commentForm.reset();
                this.commentCreated.emit();
            },
            error => handleValidationErrors(error, this.commentForm)
        )
    }

    private update(comment: CommentDetailsModel) {
        const commentData: CommentFormDataModel = {...this.commentForm.value};
        console.log(commentData);
        commentData.postId = this.postId;
        commentData.authorId = this.authorId;
        this.blogService.updateComment(commentData, comment.id).subscribe(
            () => {
                this.commentForm.reset();
                this.commentCreated.emit();
            },
            error => handleValidationErrors(error, this.commentForm),
        );
    }
}
