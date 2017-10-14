import {Component} from '@angular/core';
import {AuthenticationService} from "../common/services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'dice-nav',
  templateUrl: 'dev/navbar/navbar.component.html',
  styleUrls: ['../app/css/navbar.css']
})
export class NavbarComponent {
  private userInfo = {};

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router) {
    if(authenticationService.isLoggedIn()) {
      authenticationService.refresh()
        .subscribe(
          data => {
            this.userInfo = data;
          }
        )
    }
  }

  public onLogout(): void {
    this.authenticationService.logout();
    this.router.navigate(['home']);
  }
}

