import {Component} from 'angular2/core';
import {HomeComponent} from "./home/home.component";
import {ROUTER_DIRECTIVES, RouteConfig} from "angular2/router";
import {ProfileComponent} from "./profile/profile.component";
import {NavbarComponent} from "./navbar/navbar.component";
import {SearchComponent} from "./search/search.component";
import {FooterComponent} from "./footer/footer.component";

@Component({
    selector: 'dice-app',
    templateUrl: 'dev/app.component.html',
    directives: [ROUTER_DIRECTIVES, NavbarComponent, HomeComponent, ProfileComponent, FooterComponent]
})
@RouteConfig([
  { path: '/home', name: 'Home', component: HomeComponent },
  { path: '/profile', name: 'Profile', component: ProfileComponent },
  { path: '/search', name: 'Search', component: SearchComponent }
])
export class AppComponent {

}
