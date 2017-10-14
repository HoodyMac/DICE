import {Component} from '@angular/core';
import {TranslateService} from "ng2-translate";

@Component({
    selector: 'dice-footer',
    templateUrl: 'dev/footer/footer.component.html',
    styleUrls: ['../app/css/footer.css']
})
export class FooterComponent{

  constructor(private translate: TranslateService) {}

  useLanguage(language: string) {
    this.translate.use(language);
    localStorage.setItem('lang', language);
  }
}

