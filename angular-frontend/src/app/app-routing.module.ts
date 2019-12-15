import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PostFormComponent } from './components/post-form/post-form.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { PostDetailsComponent } from './components/post-details/post-details.component';


const routes: Routes = [
    {path: 'posts', component: PostListComponent},
    {path: 'posts/:id', component: PostDetailsComponent},
    {path: 'postForm', component: PostFormComponent},
    {path: '', redirectTo: 'posts', pathMatch: 'full'},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
