import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "./http-client.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class LikeService {

    constructor(private http: HttpClient) {}

    createLike(id: number, type) {
        console.log(type);
        return this.http.post('/api/like/'+id, type).map(res => res.json());
    }

    handleLikeCreation(post, userProfileId, response) {
        if (this.findAlreadyLiked(post, userProfileId).length > 0) {
            post.likes = post.likes.filter(like => like.user.userId !== userProfileId);
        }else
            post.likes.push(response);
    }

    findAlreadyLiked(post, userProfileId) {
        return post.likes.filter(like => like.user.userId === userProfileId);
    }
}