import {Component} from '@angular/core';
import {AuthenticationService} from "../common/services/authentication.service";
import {Router} from "@angular/router";
import {userInfo} from "os";

@Component({
  selector: 'dice-nav',
  templateUrl: 'dev/navbar/navbar.component.html',
  styleUrls: ['../app/css/navbar.css']
})
export class NavbarComponent {
  private userInfo = {};
  private searchData = {fullName: ""};

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router) {
    if(authenticationService.isLoggedIn()) {
      authenticationService.refresh()
        .subscribe(
          data => {
            this.userInfo = data;
          },
          err => this.onLogout()
        );
    }
  }

  public onLogout(): void {
    this.authenticationService.logout();
    this.router.navigate(['home']);
  }

  public goSearch(param){
    this.router.navigate(['/search', { fullname: param }] );
  }

  public gotoMyProfile() {
    this.router.navigate(['/profile', this.userInfo.userProfileId]);
  }
}
