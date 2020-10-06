import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {BlogService} from '../../services/blog.service';
import {handleValidationErrors} from '../../shared/validation.handler';
import {CategoryOptionModel} from '../../models/categoryOption.model';
import {TypeOptionModel} from '../../models/typeOption.model';
import {PostFormInitDataModel} from '../../models/postFormInitData.model';
import {UserService} from '../../services/user.service';
import {PostFormDataModel} from "../../models/postFormData.model";


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
    uploaded: string
    isUploading: boolean;
    postId: number;
    userId: number;
    isEdited: boolean = false;

    constructor(private formBuilder: FormBuilder,
                private blogService: BlogService,
                private userService: UserService,
                private router: Router,
                private activatedRoute: ActivatedRoute) {
        this.postForm = formBuilder.group({
            category: [null, Validators.required],
            type: [null, Validators.required],
            title: ['', Validators.required],
            postBody: ['', Validators.required],
            picture: [null],
            //          pictureUrl: [null],
            address: formBuilder.group({
                country: [''],
                zipCode: [''],
                city: [''],
                street: [''],
                number: [''],
                coordinate: [''],
            }),
        });
    }

    ngOnInit() {
        this.isEdited = false;
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
            this.userId = +localStorage.getItem('userId')
        } else {
            this.router.navigate(['']);
        }
        this.blogService.fetchPostFormInitData().subscribe(
            (data: PostFormInitDataModel) => {
                this.categories = data.categories;
                this.types = data.types;
            },
            error => console.warn(error),
        );
        this.authorId = +localStorage.getItem('userId');
        this.uploaded = 'folder'
        this.isUploading = false;
        this.activatedRoute.paramMap.subscribe(
            paramMap => {
                const editablePostId = paramMap.get('id');
                if (editablePostId) {
                    this.postId = +editablePostId;
                    this.getPostFormData(editablePostId);
                }
            },
            error => console.warn(error),
        );
    }

    onFileChange($event: Event) {
        const picture= null;
        this.uploaded= 'folder';
        // @ts-ignore
        if (event.target.files.length > 0) {
            // @ts-ignore
            const picture = event.target.files[0];
            this.postForm.patchValue({
                picture,
            });
            this.uploaded = 'done'

        }
    }

    onSubmit() {
        this.upload();

    }

    getTypeValue(type: string) {
        if (this.postForm.get('type').value == 'EVENT') {
            this.isEvent = true;
        } else {
            this.isEvent = false;
        }
    }

    upload() {
        this.isUploading = true;
        const formData = new FormData();
        formData.append('category', this.postForm.get('category').value);
        console.log(this.postForm.get('category').value);
        formData.append('type', this.postForm.get('type').value);
        formData.append('title', this.postForm.get('title').value);
        formData.append('postBody', this.postForm.get('postBody').value);
        if (this.postForm.get('picture').value) {
            formData.append('picture', this.postForm.get('picture').value);
        }
        formData.append('address.country', this.postForm.get('address.country').value);
        formData.append('address.zipCode', this.postForm.get('address.zipCode').value);
        formData.append('address.city', this.postForm.get('address.city').value);
        formData.append('address.street', this.postForm.get('address.street').value);
        formData.append('address.number', this.postForm.get('address.number').value);
        formData.append('authorId', '' + this.authorId);

        console.log(formData);
        if (this.postId) {
            console.log(this.postId);
            this.blogService.updatePost(formData, this.postId).subscribe(
                (response) => {
                    console.log('update started');
                    console.log(response);
                    this.router.navigate(['/posts']);
                },
                error => {
                    handleValidationErrors(error, this.postForm);
                    this.isUploading = false;
                },
                () => {
                    this.isUploading = false;
                }
            );
        } else {
            console.log('save clicked');
            this.blogService.createPost(formData).subscribe(
                () => {
                    console.log(this.postForm);
                    this.router.navigate(['/posts']);
                },
                error => {
                    handleValidationErrors(error, this.postForm);
                    this.isUploading = false;
                },
                () => {
                    this.isUploading = false;
                }
            );
        }
    }

    getPostFormData = (id: string) => {
        this.blogService.fetchPostFormData(id).subscribe(
            (response: PostFormDataModel) => {
                console.log(response);
                if (response.type === 'EVENT') {
                    this.isEvent = true;
                }
                this.isEdited = true;
                this.authorId = response.authorId;
                this.postForm.patchValue({
                    category: response.category,
                    type: response.type,
                    title: response.title,
                    postBody: response.postBody,
                    address: ({
                        country: response.address.country,
                        zipCode: response.address.zipCode,
                        city: response.address.city,
                        street: response.address.street,
                        number: response.address.number,
                        coordinate: response.address.coordinate,
                    }),
                });
            },
        );
    };

    cancel() {
        this.router.navigate(['/posts']);
    }
}
