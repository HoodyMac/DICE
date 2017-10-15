import {Component, Directive, OnInit} from '@angular/core';
import {AuthenticationService} from "../common/services/authentication.service";
import {RegistrationService} from "../common/services/registration.service";
import {
  NG_VALIDATORS, AbstractControl, Validators, FormGroup, FormBuilder,} from "@angular/forms";
import {Router} from "@angular/router";


function passwordMatcher(c: AbstractControl){
  if(!c.get('password') || !c.get('confirmPassword')) return null;
  return c.get('password').value === c.get('confirmPassword').value ? null : {'nomatch' : true};
}

@Directive({
  selector: '[password-matcher]',
  providers: [{provide: NG_VALIDATORS, multi: true, useValue: passwordMatcher},]
})

export class PasswordMatcher{

}

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
  public loginMessage: string;

  constructor(
    private authenticationService: AuthenticationService,
    private registrationService: RegistrationService,
    private route: Router,
    private fb:FormBuilder) {
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
      this.route.navigate(['profile']);
    }
  }

  onSignIn(credentials) {
    this.authenticationService.login(credentials).subscribe(
      () => this.route.navigate(['profile']),
      () => {
        this.loginMessage = "Incorrect email or password.";
        this.showLoginMessage = true;
      }
    );
  }

  onSignUp(credentials){
    this.registrationService.doSignUp(credentials)
      .subscribe(
        () => {
          this.registrationMessage = "Your account has been registered.";
          this.showRegistrationMessage = true;
        },
        error =>{
          this.errorMessage = error._body;
          this.showErrorMessage = true;
        }
      );
  }
}

