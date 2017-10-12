import {Component} from '@angular/core';
import {AuthenticationService} from "../common/services/authentication.service";

@Component({
  selector: 'dice-nav',
  templateUrl: 'dev/navbar/navbar.component.html',
  styleUrls: ['../app/css/navbar.css']
})
export class NavbarComponent {
  private userInfo = {};

  constructor(private authenticationService: AuthenticationService) {
    if(authenticationService.isLoggedIn()) {
      authenticationService.refresh()
        .subscribe(
          data => {
            this.userInfo = data;
          }
        )
    }
  }
}

