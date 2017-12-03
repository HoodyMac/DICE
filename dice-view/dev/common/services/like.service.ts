import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "./http-client.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class LikeService {

    constructor(private http: HttpClient) {}

    createLike(id: number){
        return this.http.post('/api/like/'+id, null).map(res => res.json());
    }
}