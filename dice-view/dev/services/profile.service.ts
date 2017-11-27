import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "../common/services/http-client.service";
import {Observable} from "rxjs/Observable";
import { retry } from 'rxjs/operator/retry';
import { posix } from 'path';

@Injectable()
export class ProfileService {

  constructor(private http: HttpClient) {}

  getModule(url) {
    /**
     {
        label:string; ('Friends' , 'Messages', 'Groups', 'Music', 'Photos')
        value:number; (55, 55, 86, 42, 74);
     }
     */
    return this.http.get(url).map(res => res.json());
  }

  getUserInfo(id: number) {
    return this.http.get("/api/profile/" + id).map(res => {
      return res.json();
    });
  }

  postCordsImageCrop(cropImgData: any) {
    return this.http.post('/api/profile/image/crop', cropImgData).map(res => res.json());
  }

  uploadProfilePicture(formData: any): Observable {
    return this.http
      .postWithOptions('/api/profile/image/', formData, {
        headers: {
          'Authorization': localStorage.getItem('token')
        }
      }).map(resp => resp.json());
  }

  getPosts(id: number){
    return this.http.get('/api/posts/'+id).map(res => res.json());
  }

  createUserPost(postDTO: any){
    return this.http.post('/api/posts/post', postDTO).map(res => res.json());
  }

  editPost(id: number, postDTO: any){
    return this.http.put('/api/posts/post/'+id, postDTO).map(res => res.json());
  }

  deletePost(id: number){
    return this.http.delete('/api/posts/post/'+id);
  }

}
