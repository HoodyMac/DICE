import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../common/services/authentication.service";
import {RegistrationService} from "../services/registration.service";
import {Validators, FormGroup, FormBuilder,} from "@angular/forms";
import {Router, NavigationEnd, ActivatedRoute} from "@angular/router";
import { Title } from '@angular/platform-browser';
import {TranslateService} from "ng2-translate";


@Component({
  selector: 'reactive-forms-comp',
  templateUrl: 'dev/home/home.component.html',
  styleUrls: ['../app/css/home.css']
})

export class HomeComponent implements OnInit {
  public form: FormGroup;

  public showRegistrationMessage: boolean = false;
  public registrationMessage: string;
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
        firstname: ['', Validators.compose([ Validators.required, Validators.maxLength(60)])],
        lastname: ['',Validators.compose([ Validators.required, Validators.maxLength(60)])],
        gender: ['', Validators.required],
        birthdayDate: ['', Validators.required],
        email: ['', Validators.required],
        password: ['', Validators.required],
        confirmPassword: ['', Validators.required]
      });
  }

  ngOnInit(): void {
    if(this.authenticationService.isLoggedIn()) {
      this.route.navigate(['/profile', localStorage.getItem('profileId')]);
    }
  }

  onSignIn(credentials) {
    this.authenticationService.login(credentials).subscribe(
      () => this.route.navigate(['/profile', localStorage.getItem('profileId')]),
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
        () => {
          this.showRegistrationMessage = true;
          setTimeout(function() {
            this.showRegistrationMessage = false;
          }.bind(this), 5000);
        },
        error =>{
          this.errorMessage = error._body;
          this.showErrorMessage = true;
          setTimeout(function() {
            this.showErrorMessage = false;
          }.bind(this), 5000);
        }
      );
  }
}

