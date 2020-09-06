import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BlogService} from '../../services/blog.service';
import {handleValidationErrors} from '../../shared/validation.handler';
import {UserService} from "../../services/user.service";

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

    constructor(private formBuilder: FormBuilder, private blogService: BlogService, private userService: UserService) {
        this.commentForm = formBuilder.group({
                commentBody: ['', Validators.required]
            }
        )
    }

    ngOnInit() {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }

        this.authorId = +localStorage.getItem('userId');
    }

    onSubmit() {
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
}
