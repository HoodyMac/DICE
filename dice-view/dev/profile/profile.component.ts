import {Component, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import {ProfileService} from "../services/profile.service";
import {Router} from '@angular/router';
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
  userInfo = {};
  countModules: any;
  editImgSrc: string = "/app/img/edit_icon_gray.png";
  @ViewChild('cropbox') cropbox: ElementRef;

  private jcropApi: any;
  viewImageChangeMessage: boolean = false;


  constructor(
    private profileService: ProfileService,
    private _router: Router) {

    this.profileService.getUserInfo("me").subscribe(
      data => {
          this.userInfo = data;
          if(this.jcropApi !== undefined) {
            this.jcropApi.setImage('/api/profile/image/get/' + this.userInfo.originalImgSrc);
          }
        },
        err => {
          console.log('Something went wrong!');
        }
    );

    // this.profileService.getModule("server_url").subscribe(module => {
    //       this.countModules = module;
    //     },
    //     err => {
    //       console.log('Something went wrong!');
    //     }
    // );

/* ######## EXAMPLES ################### */
    this.countModules = [{
      label: "Friends",
      value: 45
    }];

/* ################################## */

  };


  ngAfterViewInit() {
    var that = this;
    if( jQuery(this.cropbox.nativeElement).length > 0){
      jQuery(this.cropbox.nativeElement).Jcrop({
        aspectRatio: 1,
        onSelect: updateCoords
      }, function() {
        that.jcropApi = this;
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
       (data) => {
         this.userInfo.cropImgSrc = data.newImageFileName;
         this.viewImageChangeMessage = true;
         setTimeout(function() {
           this.viewImageChangeMessage = false;
         }.bind(this), 6000);
         console.log(data);
       }
     );
  };

  upload() {
    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    let formData = new FormData();
    if (fileCount > 0) { // a file was selected
      for (let i = 0; i < fileCount; i++) {
        formData.append('file', inputEl.files.item(i));
      }
      this.profileService.uploadProfilePicture(formData)
        .subscribe(
          (data) => {
            this.userInfo.originalImgSrc = data.newImageFileName;
            this.jcropApi.setImage('/api/profile/image/get/' + this.userInfo.originalImgSrc);
          }
        );
    }
  }

  editProfile(){
    this._router.navigate(['/edit']);
  }
}
