import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {HttpClient} from "./common/services/http-client.service";
import {AuthenticationService} from "./common/services/authentication.service";
import {RegistrationService} from "./common/services/registration.service";
import {RouterModule, Routes} from "@angular/router";
import {ProfileComponent} from "./profile/profile.component";
import {HomeComponent, PasswordMatcher} from "./home/home.component";
import {SearchComponent} from "./search/search.component";
import {NavbarComponent} from "./navbar/navbar.component";
import {FooterComponent} from "./footer/footer.component";
import {NavbarService} from "./common/services/navbar.service";
import {FriendsComponent} from "./friends/friends.component";
import {EditComponent} from "./edit/edit.component";



const appRoutes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'search', component: SearchComponent },
  { path: 'friends', component: FriendsComponent },
  { path: 'edit', component: EditComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full'}
];

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    RouterModule.forRoot(
      appRoutes
    )
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    ProfileComponent,
    FriendsComponent,
    EditComponent,
    SearchComponent,
    NavbarComponent,
    FooterComponent,
    PasswordMatcher
  ],
  providers: [HttpClient, AuthenticationService, NavbarService, RegistrationService],
  bootstrap: [AppComponent]

})
export class AppModule {
}
