import {NavbarService} from "./common/services/navbar.service";
import {Component} from "@angular/core";

@Component({
    selector: 'dice-app',
    templateUrl: 'dev/app.component.html',
})
export class AppComponent {

  constructor(public nav: NavbarService) {}
}
