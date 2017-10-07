import {Injectable} from '@angular/core';
import {HttpClient} from "./http-client.service";
import 'rxjs/Rx';
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthenticationService {
  constructor(private _http: HttpClient) {}

  doLogin(credentials: any): Observable {
    return this._http.post('/auth', credentials)
      .map(res => {
        const data = res.json();
        if(data) {
          localStorage.setItem('token', data.token)
        }
      });
  }
}
