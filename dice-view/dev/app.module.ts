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
import {MessagesComponent} from "./messages/messages.component";
import {ChatService} from "./services/chat.service";
import {MonacoEditorModule} from 'ngx-monaco-editor';
import {ForumListComponent} from "./forum-list/forum-list.component";
import {ForumPostComponent} from "./forum-post/forum-post.component";
import {MultiselectDropdownModule} from 'angular-2-dropdown-multiselect';

const appRoutes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'profile/:id', component: ProfileComponent },
  { path: 'search', component: SearchComponent },
  { path: 'friends', component: FriendsComponent },
  { path: 'edit', component: EditComponent },
  { path: 'messages', component: MessagesComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  { path: 'forum-list', component: ForumListComponent},
  { path: 'forum-post', component: ForumPostComponent}
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
    ModalModule,
    MonacoEditorModule,
    MultiselectDropdownModule
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
    PasswordMatcher,
    MessagesComponent,
    ForumListComponent,
    ForumPostComponent
  ],
  providers: [HttpClient, AuthenticationService, RegistrationService, ChatService],
  bootstrap: [AppComponent]

})
export class AppModule {
}
