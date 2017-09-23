import {Injectable} from '@angular/core';
import {HttpClient} from "./http-client.service";
import 'rxjs/Rx';

@Injectable()
export class AuthenticationService {
  constructor(private _http: HttpClient) {}

  doLogin(credentials: any): void {
    this._http.post('/auth', credentials)
      .map(res => {
        console.log(res);
        return res.json();
      })
      .subscribe(
        data => {
          console.log(data);
          if(data) {
            localStorage.setItem('token', data.token)
          }
        }
      );
  }
}
