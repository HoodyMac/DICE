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
  private searchData = {fullName: ""};
  private profileId;

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router) {
      this.profileId = localStorage.getItem("profileId");
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

  goSearch(param){
    this.router.navigate(['/search', { fullname: param }] );
  }
}

