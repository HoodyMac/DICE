import {Injectable} from '@angular/core';
import {HttpClient} from "./http-client.service";
import 'rxjs/Rx';

@Injectable()
export class AuthenticationService {
  constructor(private _http: HttpClient) {}

  doLogin(credentials: any): void {
    this._http.post('/auth', credentials)
      .map(res => res.json())
      .subscribe(
        data => {
          if(data) {
            localStorage.setItem('token', data.token)
          }
        }
      );
  }
}
