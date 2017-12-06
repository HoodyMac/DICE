import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../common/services/authentication.service";
import {RegistrationService} from "../services/registration.service";
import {Validators, FormGroup, FormBuilder, Validator,} from "@angular/forms";
import {Router, NavigationEnd, ActivatedRoute} from "@angular/router";
import { Title } from '@angular/platform-browser';
import {TranslateService} from "ng2-translate";


@Component({
  selector: 'reactive-forms-comp',
  templateUrl: 'dev/home/home.component.html',
  styleUrls: ['../app/css/home.css']
})

export class HomeComponent {
  public form: FormGroup;
  public currentDate: any = Date.now();

  public errorMessage: string;
  public showErrorMessage: boolean = false;
  public showLoginMessage: boolean = false;

  constructor(
    private authenticationService: AuthenticationService,
    private registrationService: RegistrationService,
    private route: Router,
    private fb:FormBuilder,
    private titleService: Title,
    private translate: TranslateService) {

      translate.get('PAGE_TITLES.HOME').subscribe((res: string) => {
          this.titleService.setTitle(res);
      });

      this.form = this.fb.group({
        firstname: ['', Validators.compose([ Validators.required, Validators.maxLength(60), Validators.minLength(3)])],
        lastname: ['',Validators.compose([ Validators.required, Validators.maxLength(60), Validators.minLength(3)])],
        gender: ['', Validators.required],
        birthdayDate: ['', Validators.required],
        email: ['', Validators.required],
        password: ['', Validators.compose([ Validators.required, Validators.minLength(3)])],
        confirmPassword: ['', Validators.required]
      });
  }
    useLanguage(language: string) {
        this.translate.use(language);
        localStorage.setItem('lang', language);
    }

  onSignIn(credentials) {
    this.authenticationService.login(credentials).subscribe(
      data => this.route.navigate(['/profile', data.userProfileId]),
      () => {
        this.showLoginMessage = true;
        setTimeout(function() {
          this.showLoginMessage = false;
        }.bind(this), 5000);
      }
    );
  }

  onSignUp(credentials){
    this.registrationService.doSignUp(credentials)
      .subscribe(
        () => this.onSignIn({
          email: credentials.email,
          password: credentials.password
        }),
        error => {
          console.log(error);
          this.errorMessage = error._body;
          this.showErrorMessage = true;
          setTimeout(function() {
            this.showErrorMessage = false;
          }.bind(this), 5000);
        }
      );
  }
}

