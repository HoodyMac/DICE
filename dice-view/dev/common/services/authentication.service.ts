import {Injectable} from '@angular/core';
import {HttpClient} from "./http-client.service";
import 'rxjs/Rx';
import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";

@Injectable()
export class AuthenticationService {
  private userInfo;

  private userInfoObservable: Observable;
  private userInfoSubject: Subject;

  constructor(private _http: HttpClient) {
    this.userInfoSubject = new Subject();
    this.userInfoObservable = this.userInfoSubject.asObservable();
  }

  login(credentials: any): Observable {
    return this._http.post('/auth', credentials)
      .map(res => {
        const data = res.json();
        if(data) {
          localStorage.setItem('token', data.token);
        }
        this.userInfo = data.userInfo;
        this.userInfoSubject.next(this.userInfo);
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
          this.userInfoSubject.next(this.userInfo);
          return this.userInfo;
        }
      );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.userInfo = {};
    this.userInfoSubject.next(this.userInfo);
  }

  getUserInfo() {
    return this.userInfo;
  }

  getUserInfoObservable(): Observable {
    return this.userInfoObservable
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
}
