import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BlogService } from '../../services/blog.service';
import { handleValidationErrors } from '../../shared/validation.handler';

@Component({
    selector: 'app-post-form',
    templateUrl: './post-form.component.html',
    styleUrls: ['./post-form.component.css'],
})
export class PostFormComponent implements OnInit {

    postForm: FormGroup;

    constructor(private formBuilder: FormBuilder, private blogService: BlogService, private router: Router) {
        this.postForm = formBuilder.group({
            title: ['', Validators.required],
            postBody: ['', Validators.required],
            imgUrl: [''],
        });
    }

    ngOnInit() {}

    onSubmit() {
        this.blogService.createPost(this.postForm.value).subscribe(
            () => this.router.navigate(['/posts']),
            error => handleValidationErrors(error, this.postForm)
        )
    }
}
