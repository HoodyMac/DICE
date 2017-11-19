import {Component, ViewChild, ElementRef, AfterViewInit} from '@angular/core';
import {EditService} from "../services/edit.service";
import {AuthenticationService} from "../common/services/authentication.service";
import {ActivatedRoute, Router} from '@angular/router';
import {FormGroup, FormBuilder} from "@angular/forms";
import {TranslateService} from "ng2-translate";
import {Title} from "@angular/platform-browser";
let clicked = true;
declare var jQuery: any;


@Component({
  templateUrl: 'dev/edit/edit.component.html',
  styleUrls: ['../app/css/edit.css'],
  providers: [EditService]
})
export class EditComponent implements AfterViewInit {
  @ViewChild('choosenSelect') choosenSelect: ElementRef;
  showEdit: boolean = true;
  showGeneral: boolean = false;
  userBasicInfo = {programmingLanguages: null};
  userGeneralInfo = {};
  showEditMessage: boolean = false;
  showEditPassMessage: boolean = false;
  showEditEmailMessage: boolean = false;
  progLang: Object;
  public passwordData: FormGroup;

    constructor(
        private authenticationService: AuthenticationService,
        private editService: EditService,
        private router: Router,
        private fb:FormBuilder,
        private titleService: Title,
        private translate: TranslateService) {

        translate.get('PAGE_TITLES.EDIT').subscribe((res: string) => {
            this.titleService.setTitle(res);
        });

        this.authenticationService.getUserInfoObservable().subscribe(user => {
          this.editService.getUserBasicInfo(user.userProfileId).subscribe(value => {
              this.userBasicInfo = value;

            },
            err => {
              console.log('Something went wrong!');
            }
          );
        });

    this.passwordData = this.fb.group({
      password: [''],
      confirmPassword: [''],
      oldPassword: ['']
    });

  }

  saveUserBasicInfo() {
    this.progLang = jQuery(this.choosenSelect.nativeElement).val();
    this.userBasicInfo.programmingLanguages = this.progLang.toString();
    this.editService.setUserBasicInfo(this.userBasicInfo)
      .subscribe(
        data => {
          this.userBasicInfo = data;
          this.showEditMessage = true;
          setTimeout(function () {
            this.showEditMessage = false;
          }.bind(this), 5000);
        }
      );

  }

  saveUserEmail(editGeneralData: Object) {
    console.log(editGeneralData);
    this.editService.setUserEmail(editGeneralData).subscribe(
      () => {
        this.showEditEmailMessage = true;
        setTimeout(function () {
          this.showEditEmailMessage = false;
        }.bind(this), 5000);
      },
      () => {
        console.log("Something went wrong!");
      }
    );
  }

  saveUserPassword(editUserPass) {
    this.editService.setUserPassword(editUserPass).subscribe(
      success => {
        this.showEditPassMessage = true;
        setTimeout(function () {
          this.showEditPassMessage = false;
        }.bind(this), 5000);
      },
      error => {
        console.log(error._body);
      }
    );
  }

  // jQuery Chosen initialize...
  ngAfterViewInit() {
    if (jQuery(this.choosenSelect.nativeElement).length > 0) {
      jQuery(this.choosenSelect.nativeElement).chosen();
    }
  }

  // toggle main content for 'Edit' page
  showEditSettings() {
    this.showEdit = true;
    this.showGeneral = false;
  }

  showGeneralSettings() {
    this.showEdit = false;
    this.showGeneral = true;
  }

  // open and close modal window
  toggle(id): void {
    let modal = document.getElementById(id);
    if (clicked) {
      modal.style.display = 'block';
      clicked = false;
    }
    else {
      modal.style.display = 'none';
      clicked = true;
    }
  }

  public gotoMyProfile() {
    this.router.navigate(['/profile', this.userBasicInfo.userId]);
  }
}
