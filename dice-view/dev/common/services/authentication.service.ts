import {Injectable} from '@angular/core';
import {HttpClient} from "./http-client.service";
import 'rxjs/Rx';
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthenticationService {
  private userInfo = {};

  constructor(private _http: HttpClient) {}

  login(credentials: any): Observable {
    return this._http.post('/auth', credentials)
      .map(res => {
        const data = res.json();
        if(data) {
          localStorage.setItem('token', data.token);
        }
        this.userInfo = data.userInfo;
      });
  }

  refresh(): void {
    return this._http.get('/auth/refresh')
      .map(
        res => {
          const data = res.json();
          if(data) {
            localStorage.setItem('token', data.token);
          }
          this.userInfo = data.userInfo;
          return this.userInfo;
        }
      );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.userInfo = {};
  }

  getUserInfo() {
    return this.userInfo;
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');

  }
}
