import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "../common/services/http-client.service";
import {Observable} from "rxjs/Observable";

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

  getUserInfo(id: any) {
    /**
     {
        originalImgSrc: string;
        cropImgSrc: string;
        username:string;
        city:string;
        education:string;
        work:string;
        age:string;
        prgLanguages:string;
        phoneNumber:string;
        isOnline:boolean;
    }
     */
    if (id != "me") {
      return this.http.get("/api/user/" + id).map(res => {
        return res.json();
      });
    } else
      return this.http.get("/api/me").map(res => {
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
}
