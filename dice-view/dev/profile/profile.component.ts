import {Component} from '@angular/core';
import {ProfileService} from "../services/profile.service";
let clicked = true;

@Component({
  templateUrl: 'dev/profile/profile.component.html',
  styleUrls: ['../app/css/profile.css'],
  providers: [ProfileService]
})

export class ProfileComponent {
  userInfo: any; //userInfo[];
  countModules: any; //count_module[];
  userLabel: Object;
  modalWindowTitle: string;
  croppedImgSrc: Object;

  constructor(private profileService: ProfileService) {
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
      isOnline: true
    }];

/* ################################## */

    this.userLabel = {
      cityLabel: "City",
      educationLabel: "Education",
      workLabel: "Work",
      ageLabel: "Age",
      prgLangLabel: "Programming languages",
      phoneNumberLabel: "Phone number"
    };

    this.modalWindowTitle = "Crop your profile photo";

  };

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
  isOnline:boolean;
}

interface count_module{
  label:string;
  value:number;
}