import {Component} from "@angular/core";
import {AuthenticationService} from "./common/services/authentication.service";
import {TranslateService} from 'ng2-translate';
import {NavigationEnd, Router} from "@angular/router";

@Component({
    selector: 'dice-app',
    templateUrl: 'dev/app.component.html',
})
export class AppComponent {

  constructor(public authenticationService: AuthenticationService,
              private translate: TranslateService,
              private router: Router) {
    translate.addLangs(['en', 'pl', 'ua', 'ru']);
    translate.setDefaultLang('pl');
    const lang = localStorage.getItem('lang');
    if(lang) {
      translate.use(lang);
    }

    this.router.events.filter(event => event instanceof NavigationEnd).subscribe(
      () => {
        const isLoggedIn: boolean = this.authenticationService.isLoggedIn();
        const isAtHomePage: boolean = this.router.url === '/home';
        if(isLoggedIn && isAtHomePage) {
          this.router.navigate(['profile']);
        } else if(!(isLoggedIn || isAtHomePage)) {
          this.router.navigate(['home']);
        }
      }
    );
  }
}
