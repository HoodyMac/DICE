import {Component} from 'angular2/core';
import {HomeComponent} from "./home/home.component";
import {ROUTER_DIRECTIVES, RouteConfig} from "angular2/router";
import {ProfileComponent} from "./profile/profile.component";
import {NavbarComponent} from "./navbar/navbar.component";

@Component({
    selector: 'dice-app',
    templateUrl: 'dev/app.component.html',
    directives: [ROUTER_DIRECTIVES, NavbarComponent, HomeComponent, ProfileComponent]
})
@RouteConfig([
  { path: '/home', name: 'Home', component: HomeComponent },
  { path: '/profile', name: 'Profile', component: ProfileComponent }
])
export class AppComponent {

}
