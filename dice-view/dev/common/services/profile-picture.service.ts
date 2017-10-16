import {Injectable} from '@angular/core';
import {HttpClient} from "./http-client.service";
import 'rxjs/Rx';
import {Observable} from "rxjs/Observable";

@Injectable()
export class ProfilePictureService {
  constructor(private _http: HttpClient) {}

  uploadProfilePicture(formData: any): Observable {
    return this._http
      .postWithOptions('/api/profile/image/', formData, {
        headers: {
          'Authorization': localStorage.getItem('token')
        }
      })
  }
}
