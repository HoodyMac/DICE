import {Component, OnInit} from 'angular2/core';
import {AuthenticationService} from "../authentication/authentication.service";
import {ControlGroup, FormBuilder, Validators} from "angular2/common";

@Component({
  templateUrl: 'dev/home/home.component.html',
  styleUrls: ['../app/css/home.css']
})
export class HomeComponent implements OnInit {
  public singInForm: ControlGroup;

  constructor(
    private _authenticationService: AuthenticationService,
    private _formBuilder: FormBuilder) {}

  ngOnInit(): any {
    this.singInForm = this._formBuilder.group({
      'username': ['', Validators.required],
      'password': ['', Validators.required],
    });
  }

  onSignIn(credentials) {
    this._authenticationService.doLogin(credentials);
  }
}

