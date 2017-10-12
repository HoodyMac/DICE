import {Component} from "@angular/core";
import {AuthenticationService} from "./common/services/authentication.service";

@Component({
    selector: 'dice-app',
    templateUrl: 'dev/app.component.html',
})
export class AppComponent {

  constructor(public authenticationService: AuthenticationService) {}
}
