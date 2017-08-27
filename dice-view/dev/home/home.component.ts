import {Component} from '@angular/core';
import {AuthenticationService} from "../common/services/authentication.service";
import {NavbarService} from "../common/services/navbar.service";

@Component({
  templateUrl: 'dev/home/home.component.html',
  styleUrls: ['../app/css/home.css']
})
export class HomeComponent {
  constructor(
    private _authenticationService: AuthenticationService,
    private _nav: NavbarService) {
  }

  ngOnInit(): void {
    this._nav.hide();
  }

  onSignIn(credentials) {
    this._authenticationService.doLogin(credentials);
  }
}

