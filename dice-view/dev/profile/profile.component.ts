import {Component, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import {ProfileService} from "../services/profile.service";
import {Router, ActivatedRoute } from '@angular/router';
import {SearchService} from '../services/search.service';
import {FriendsService} from '../services/friends.service';
import {TranslateService} from "ng2-translate";
import {Title} from "@angular/platform-browser";
import {AuthenticationService} from "../common/services/authentication.service";

let clicked = true;
declare var jQuery: any;

@Component({
  templateUrl: 'dev/profile/profile.component.html',
  styleUrls: ['../app/css/profile.css'],
  providers: [ProfileService, SearchService, FriendsService]
})

export class ProfileComponent implements AfterViewInit{
  @ViewChild('fileInput') inputEl: ElementRef;

  countModules: any; //count_module[];
  userInfo = {};
  editImgSrc: string = "/app/img/edit_icon_gray.png";
  profileId;
  isMe: boolean;
  @ViewChild('cropbox') cropbox: ElementRef;

  private jcropApi: any;
  viewImageChangeMessage: boolean = false;


  constructor(
    private profileService: ProfileService,
    private authenticationService: AuthenticationService,
    private _router: Router, private route: ActivatedRoute, private _searchService: SearchService,
    private _friendService: FriendsService,
    private titleService: Title,
    private translate: TranslateService) {


    this.route.params.subscribe(params => {
      this.profileId = params['id'];
    });

    authenticationService.getUserInfoObservable().subscribe(
      data => this.isMe = this.profileId == data.userProfileId
    );

    this.profileService.getUserInfo(this.profileId).subscribe(
      data => {
          this.userInfo = data;
          if(this.jcropApi !== undefined) {
            this.jcropApi.setImage('/api/profile/image/get/' + this.userInfo.originalImgSrc);
          }
          translate.get('PAGE_TITLES.PROFILE', {username: this.userInfo.firstname + " " + this.userInfo.lastname } ).subscribe((res: string) => {
            this.titleService.setTitle(res);
          });
        },
        err => {
          console.log('Something went wrong!');
        }
    );
  };

  goToMessagePage(){
    this._router.navigate(['/messages', {redirectToChat: this.profileId}]);
  }

  addToFriends(){
    this._searchService.addToFriends(this.profileId).subscribe();
  }

  cancelFriendRequest(){
    this._friendService.removeFriend(this.profileId).subscribe();
  }

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
