import {Injectable} from 'angular2/core';
import 'rxjs/Rx';
import {HttpClient} from "./http-client.service";


@Injectable()
export class AuthenticationService {
  constructor(private _http: HttpClient) {}

  doLogin(credentials): void {
    this._http.post('/auth', credentials)
      .map(
        res => res.json()
      ).subscribe(
        data => {
          if(data) {
            localStorage.setItem('token', data.token)
          }
        }
      );
  }
}
