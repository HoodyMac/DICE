import {Component, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import {ProfileService} from "../services/profile.service";
import {AuthenticationService} from "../common/services/authentication.service";
import {Router} from '@angular/router';
import {userInfo} from "os";
import {ProfilePictureService} from "../common/services/profile-picture.service";
let clicked = true;
declare var jQuery: any;

@Component({
  templateUrl: 'dev/profile/profile.component.html',
  styleUrls: ['../app/css/profile.css'],
  providers: [ProfileService]
})

export class ProfileComponent implements AfterViewInit{
  @ViewChild('fileInput') inputEl: ElementRef;

  userInfo: any; //userInfo[];
  countModules: any; //count_module[];
  userLabel: Object;
  modalWindowTitle: string;
  userInfo = {};
  countModules: any;
  croppedImgSrc: Object;
  editImgSrc: string = "/app/img/edit_icon_gray.png";
  @ViewChild('cropbox') cropbox: ElementRef;


  constructor(
    private profileService: ProfileService,
    private authenticationService: AuthenticationService,
    private _router: Router,
    private profilePictureService: ProfilePictureService) {

    this.profileService.getUserInfo("me").subscribe(
      data => {
          console.log(data);
          this.userInfo = data;
          console.log(this.userInfo);
        },
        err => {
          console.log('Something went wrong!');
        }
    );

    this.profileService.getModule("server_url").subscribe(module => {
          this.countModules = module;
        },
        err => {
          console.log('Something went wrong!');
        }
    );

/* ######## EXAMPLES ################### */
    this.countModules = [{
      label: "Friends",
      value: 45
    }];

/* ################################## */

  };


  ngAfterViewInit() {
    if( jQuery(this.cropbox.nativeElement).length > 0){
      jQuery(this.cropbox.nativeElement).Jcrop({
        aspectRatio: 1,
        onSelect: updateCoords
      });
    }

    function updateCoords(c)
    {
      jQuery('#x').val(c.x);
      jQuery('#y').val(c.y);
      jQuery('#w').val(c.w);
      jQuery('#h').val(c.h);
    };
  }

  // change image src for edit icon
  onMouseOver(): void {
    this.editImgSrc = "/app/img/edit_icon_black.png";
  }

  onMouseOut(): void {
    this.editImgSrc = "/app/img/edit_icon_gray.png";
  }


  cropImageAndSave(x, y, w, h){
    let imgCropData = {
      "x" : x,
      "y" : y,
      "w" : w,
      "h" : h
    };
     this.profileService.postCordsImageCrop(imgCropData).subscribe(
       () => console.log("done")
     );
  };

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

  upload() {
    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    let formData = new FormData();
    if (fileCount > 0) { // a file was selected
      for (let i = 0; i < fileCount; i++) {
        formData.append('file', inputEl.files.item(i));
      }
      this.profilePictureService.uploadProfilePicture(formData)
        .subscribe(
          () => console.log('done')
        );
    }
  }

  editProfile(){
    this._router.navigate(['/edit']);
  }
}
