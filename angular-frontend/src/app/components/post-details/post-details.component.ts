import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BlogService} from '../../services/blog.service';
import {PostDetailsModel} from "../../models/postDetails.model";
import {UserService} from "../../services/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CommentDetailsModel} from "../../models/commentDetails.model";

@Component({
    selector: 'app-post-details',
    templateUrl: './post-details.component.html',
    styleUrls: ['./post-details.component.css'],
})
export class PostDetailsComponent implements OnInit {

    id: number;
    postDetails: PostDetailsModel;
    commentOpenState: boolean;
    addressOpenState: boolean;
    commentForm: FormGroup;
    userId: number
    isUnderEditing: boolean;
    editedCommentId: number;

    constructor(private formBuilder: FormBuilder,
                private blogService: BlogService,
                private router: Router,
                private route: ActivatedRoute,
                private userService: UserService) {
        this.route.paramMap.subscribe(
            map => {
                const idParam = +map.get('id');
                if (idParam && !isNaN(idParam)) {
                    this.id = idParam;
                    this.loadPostDetails();
                }
            },
        );
        this.commentForm = formBuilder.group({
                commentBody: ['', Validators.required]
            }
        )
    }

    ngOnInit() {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
            this.userId = +localStorage.getItem('userId');
        } else {
            this.router.navigate(['']);
        }
        this.isUnderEditing = false;
        this.editedCommentId = undefined;
    }

    loadPostDetails() {
        this.blogService.fetchPostDetails(this.id).subscribe(
            (data: PostDetailsModel) => this.postDetails = data,
            error => {
                this.router.navigate(['not-found']);
                console.warn(error);
            },
            () => {
                this.isUnderEditing = false;
                this.editedCommentId = undefined;
            }
        );
    }

    edit(comment: CommentDetailsModel) {
        this.editedCommentId = comment.id;
        this.isUnderEditing = true;
    }
}
