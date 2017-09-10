import {Component, Directive} from '@angular/core';
import {AuthenticationService} from "../common/services/authentication.service";
import {NavbarService} from "../common/services/navbar.service";
import {RegistrationService} from "../common/services/registration.service";
import {
  NG_VALIDATORS, AbstractControl, Validators, FormGroup, FormControl, FormBuilder,} from "@angular/forms";

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

export class HomeComponent {
  form: FormGroup;

  constructor(
    private _authenticationService: AuthenticationService,
    private registrationService: RegistrationService,
    private _nav: NavbarService,

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
    this._nav.hide();
  }

  onSignIn(credentials) {
    this._authenticationService.doLogin(credentials);
  }

  onSignUp(credentials){
    console.log(credentials);
    this.registrationService.doSignUp(credentials);
  }
}

