import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BlogService} from '../../services/blog.service';
import {PostDetailsModel} from "../../models/postDetails.model";
import {UserService} from "../../services/user.service";

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

    constructor(private blogService: BlogService,
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
    }

    ngOnInit() {
        if (localStorage.getItem('auth')) {
            this.userService.loginSubject.next();
        }else {
            this.router.navigate(['']);
        }
    }

    loadPostDetails() {
        this.blogService.fetchPostDetails(this.id).subscribe(
            (data: PostDetailsModel) => this.postDetails = data,
            error => console.warn(error),
        );
    }
}
