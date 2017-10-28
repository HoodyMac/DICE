import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Http, HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {HttpClient} from "./common/services/http-client.service";
import {AuthenticationService} from "./common/services/authentication.service";
import {RegistrationService} from "./services/registration.service";
import {RouterModule, Routes} from "@angular/router";
import {ProfileComponent} from "./profile/profile.component";
import {HomeComponent} from "./home/home.component";
import {PasswordMatcher} from "./common/components/passwordMatcher.component";
import {SearchComponent} from "./search/search.component";
import {NavbarComponent} from "./navbar/navbar.component";
import {FooterComponent} from "./footer/footer.component";
import {FriendsComponent} from "./friends/friends.component";
import {EditComponent} from "./edit/edit.component";
import {TranslateModule, TranslateStaticLoader, TranslateLoader} from "ng2-translate";
import {ModalModule} from "ng2-modal"

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
    ),
    TranslateModule.forRoot({
      provide: TranslateLoader,
      useFactory: (http: Http) => new TranslateStaticLoader(http, '/assets/i18n', '.json'),
      deps: [Http]
    }),
    ModalModule
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
  providers: [HttpClient, AuthenticationService, RegistrationService],
  bootstrap: [AppComponent]

})
export class AppModule {
}
