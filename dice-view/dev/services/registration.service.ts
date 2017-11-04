import {Injectable} from '@angular/core';
import {HttpClient} from "../common/services/http-client.service";
import 'rxjs/Rx';
import {Observable} from "rxjs/Observable";

@Injectable()
export class RegistrationService {
    constructor(private _http: HttpClient) {}

      doSignUp(credentials: any): Observable {
        return this._http.post('/api/user/save', credentials);
      }
}
