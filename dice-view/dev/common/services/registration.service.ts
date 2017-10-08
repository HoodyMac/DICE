import {Injectable} from '@angular/core';
import {HttpClient} from "./http-client.service";
import 'rxjs/Rx';

@Injectable()
export class RegistrationService{
    constructor(private _http: HttpClient) {}
    
      doSignUp(credentials: any): void {
        this._http.post('/api/user/save', credentials)
          .map(res => res.json())
          .subscribe(
            data => {
              if(data) {
                console.log("signed up");
              }
            }
          );
      }
}