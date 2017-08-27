import {Component} from '@angular/core';
import {AuthenticationService} from "../authentication/authentication.service";

@Component({
  templateUrl: 'dev/home/home.component.html',
  styleUrls: ['../app/css/home.css']
})
export class HomeComponent {

  constructor(
    private _authenticationService: AuthenticationService) {}

  onSignIn(credentials) {
    this._authenticationService.doLogin(credentials);
  }
}

