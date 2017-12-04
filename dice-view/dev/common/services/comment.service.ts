import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "./http-client.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class CommentService {

    constructor(private http: HttpClient) {}

    createComment(commentDTO: any){
        return this.http.post('/api/comments/comment', commentDTO).map(res => res.json());
    }

    getComments(id){
        return this.http.get('/api/comments/post/'+id).map(res => res.json());
    }
    
    editComment(id: number, commentDTO:any){
        return this.http.post('/api/comments/comment/'+id, commentDTO).map(res => res.json());
    }
    
    deleteComent(id: number){
        return this.http.delete('/api/comments/comment/'+id);
    }

}