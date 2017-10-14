import {Component} from "@angular/core";
import {AuthenticationService} from "./common/services/authentication.service";
import {TranslateService} from 'ng2-translate';

@Component({
    selector: 'dice-app',
    templateUrl: 'dev/app.component.html',
})
export class AppComponent {

  constructor(public authenticationService: AuthenticationService,
              private translate: TranslateService) {
    translate.addLangs(['en', 'pl', 'ua', 'ru']);
    translate.setDefaultLang('pl');
    const lang = localStorage.getItem('lang');
    if(lang) {
      translate.use(lang);
    }
  }
}
