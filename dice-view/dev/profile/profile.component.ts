import {Component, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import {ProfileService} from "../services/profile.service";
import {AuthenticationService} from "../common/services/authentication.service";
let clicked = true;
declare var jQuery: any;

@Component({
  templateUrl: 'dev/profile/profile.component.html',
  styleUrls: ['../app/css/profile.css'],
  providers: [ProfileService]
})

export class ProfileComponent implements AfterViewInit{
  userInfo: any; //userInfo[];
  countModules: any; //count_module[];
  userLabel: Object;
  modalWindowTitle: string;
  croppedImgSrc: Object;
  editImgSrc: string = "/app/img/edit_icon_gray.png";
  @ViewChild('cropbox') cropbox: ElementRef;
  @ViewChild('x') x: ElementRef;
  @ViewChild('y') y: ElementRef;
  @ViewChild('w') w: ElementRef;
  @ViewChild('h') h: ElementRef;


  constructor(
    private profileService: ProfileService,
    private authenticationService: AuthenticationService) {

    this.profileService.getUserInfo("server_url").subscribe(value => {
          this.userInfo = value;
        },
        err => {
          console.log('Something went wrong!');
        }
    );

    this.profileService.getModule("server_url").subscribe(module => {
          this.countModules = module;
        },
        err => {
          console.log('Something went wrong! ');
        }
    );

/* ######## EXAMPLES ################### */
    this.countModules = [{
      label: "Friends",
      value: 45
    }];

    this.userInfo = [{
      originalImgSrc: "/app/img/Ivan_Loichuk_bg500x500.jpg",
      cropImgSrc:  "/app/img/Ivan_Loichuk_bg.jpg",
      username: "Ivan Loichuk",
      city: "Rivne",
      education: "Politech",
      work: "Spark",
      age: 25,
      prgLanguages: "PHP",
      phoneNumber: "+752115558",
      isOnline: true
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
      jQuery(this.x).val(c.x);
      jQuery(this.y).val(c.y);
      jQuery(this.w).val(c.w);
      jQuery(this.h).val(c.h);
    };
  }

  // change image src for edit icon
  onMouseOver(): void {
    this.editImgSrc = "/app/img/edit_icon_black.png";
  }

  onMouseOut(): void {
    this.editImgSrc = "/app/img/edit_icon_gray.png";
  }


  cropImageAndSave(imgSrc, x , y, w, h){
    let imgCropData = {
      "imgSrc" : imgSrc,
      "x" : x,
      "y" : y,
      "w" : w,
      "h" : h
    };
     this.profileService.postCoordsImageCrop(imgCropData, "server_url").subscribe(src =>{
        this.croppedImgSrc = src;    // return only cropped image src
     });
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
}

interface userInfo{
  originalImgSrc: string;
  cropImgSrc: string;
  username:string;
  city:string;
  education:string;
  work:string;
  age:string;
  prgLanguages:string;
  phoneNumber:string;
  isOnline:boolean;
}

interface count_module{
  label:string;
  value:number;
}
