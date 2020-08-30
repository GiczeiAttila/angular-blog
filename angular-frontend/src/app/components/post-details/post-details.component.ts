import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BlogService} from '../../services/blog.service';
import {PostDetailsModel} from "../../models/postDetails.model";

@Component({
    selector: 'app-post-details',
    templateUrl: './post-details.component.html',
    styleUrls: ['./post-details.component.css'],
})
export class PostDetailsComponent implements OnInit {

    id: number;
    postDetails: PostDetailsModel;

    constructor(private blogService: BlogService, private route: ActivatedRoute) {
        this.route.paramMap.subscribe(
            map => {
                const idParam = +map.get('id');
                if (idParam && !isNaN(idParam)) {
                    this.id = idParam;
                    this.loadPostDetails();
                }
            },
        );
    }

    ngOnInit() {
    }

    loadPostDetails() {
        this.blogService.fetchPostDetails(this.id).subscribe(
            (data: PostDetailsModel) => this.postDetails = data,
            error => console.warn(error),
        );
    }
}
