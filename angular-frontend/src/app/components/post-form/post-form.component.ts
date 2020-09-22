import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {BlogService} from '../../services/blog.service';
import {handleValidationErrors} from '../../shared/validation.handler';
import {CategoryOptionModel} from "../../models/categoryOption.model";
import {TypeOptionModel} from "../../models/typeOption.model";
import {PostFormInitDataModel} from "../../models/postFormInitData.model";
import {UserService} from "../../services/user.service";
import {FileUploadService} from "../../services/file-upload.service";

@Component({
    selector: 'app-post-form',
    templateUrl: './post-form.component.html',
    styleUrls: ['./post-form.component.css'],
})
export class PostFormComponent implements OnInit {

    postForm: FormGroup;
    authorId: number;
    categories: Array<CategoryOptionModel>;
    types: Array<TypeOptionModel>;
    isEvent: boolean;



    constructor(private formBuilder: FormBuilder,
                private blogService: BlogService,
                private userService: UserService,
                private router: Router,
                private fileService: FileUploadService) {
        this.postForm = formBuilder.group({
            category: [null, Validators.required],
            type: [null, Validators.required],
            title: ['', Validators.required],
            postBody: ['', Validators.required],
            picture: [null],
            address: formBuilder.group({
                country: [''],
                zipCode: [''],
                city: [''],
                street: [''],
                number: [''],
                coordinate: ['']
            })
        });
    }

    ngOnInit() {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }
        this.blogService.fetchPostFormInitData().subscribe(
            (data: PostFormInitDataModel) => {
                this.categories = data.categories;
                this.types = data.types;
            },
            error => console.warn(error)
        )

        this.authorId = +localStorage.getItem('userId');
    }

    onFileChange($event: Event) {
        // @ts-ignore
        if (event.target.files.length > 0) {
            // @ts-ignore
            const picture = event.target.files[0];
            this.postForm.patchValue({
                picture
            });
        }
    }

    onSubmit() {
        const post = this.postForm.value;
        post.authorId = this.authorId;
        this.blogService.createPost(post).subscribe(
            () => {
                console.log(this.postForm);
                this.router.navigate(['/posts'])
            },
            error => handleValidationErrors(error, this.postForm)
        )

    }

    getTypeValue(type: string) {
        if (type == 'EVENT') {
            this.isEvent = true;
        } else {
            this.isEvent = false;
        }
    }

    upload() {
        const formData = new FormData();
        formData.append('picture', this.postForm.get('picture').value);
        this.fileService.uploadFile(formData).subscribe(
            console.log,
            console.warn
        );
    }
}
