import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {BlogService} from '../../services/blog.service';
import {handleValidationErrors} from '../../shared/validation.handler';
import {CategoryOptionModel} from "../../models/categoryOption.model";
import {TypeOptionModel} from "../../models/typeOption.model";
import {PostFormInitDataModel} from "../../models/postFormInitData.model";

@Component({
    selector: 'app-post-form',
    templateUrl: './post-form.component.html',
    styleUrls: ['./post-form.component.css'],
})
export class PostFormComponent implements OnInit {

    postForm: FormGroup;
    categories: Array<CategoryOptionModel>;
    types: Array<TypeOptionModel>;


    constructor(private formBuilder: FormBuilder,
                private blogService: BlogService,
                private router: Router) {
        this.postForm = formBuilder.group({
            category: [null, Validators.required],
            type: [null, Validators.required],
            title: ['', Validators.required],
            postBody: ['', Validators.required],
            picture: [''],
        });
    }

    ngOnInit() {
        this.blogService.fetchPostFormInitData().subscribe(
            (data: PostFormInitDataModel) => {
                this.categories = data.categories;
                this.types = data.types;
            },
            error => console.warn(error)
        )
    }

    onSubmit() {
        this.blogService.createPost(this.postForm.value).subscribe(
            () => {
                console.log(this.postForm);
                this.router.navigate(['/posts'])
            },
            error => handleValidationErrors(error, this.postForm)
        )
    }
}
