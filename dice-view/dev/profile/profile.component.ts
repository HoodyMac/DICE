import {Component, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import {ProfileService} from "../services/profile.service";
import {Router, ActivatedRoute} from '@angular/router';
import {SearchService} from '../services/search.service';
import {FriendsService} from '../services/friends.service';
import {AuthenticationService} from "../common/services/authentication.service";
import {CommentService} from "../common/services/comment.service";
import {LikeService} from "../common/services/like.service";
import {TranslateService} from "ng2-translate";
import {Title} from "@angular/platform-browser";
import _ from "lodash";
import {LikeService} from "../common/services/like.service";

let clicked = true;
declare var jQuery: any;

@Component({
  templateUrl: 'dev/profile/profile.component.html',
  styleUrls: ['../app/css/profile.css'],
  providers: [ProfileService, SearchService, FriendsService, CommentService, LikeService]
})

export class ProfileComponent implements AfterViewInit {
  @ViewChild('fileInput') inputEl: ElementRef;

  countModules: any; //count_module[];
  userInfo = {};
  editImgSrc: string = "/app/img/edit_icon_gray.png";
  profileId;
  isMe: boolean;
  postDTO = {};
  inPostEdit: boolean = false;
  postToEdit = {};
  commentDTO = {post: null, content: ''};
  commentsContent = [];
  showComments = false;
  currentUser = {};
  spinnerShow = [];
  showImgSpinner = false;

  @ViewChild('cropbox') cropbox: ElementRef;

  private jcropApi: any;
  viewImageChangeMessage: boolean = false;


  constructor(private profileService: ProfileService,
              private authenticationService: AuthenticationService,
              private _router: Router, private route: ActivatedRoute, private _searchService: SearchService,
              private _friendService: FriendsService,
              private titleService: Title,
              private translate: TranslateService,
              private commentService: CommentService,
              private likeService: LikeService) {

    this.route.params.subscribe(params => {
      this.profileId = params['id'];
    });

    this.currentUser = this.authenticationService.getUserInfo();
    if (this.currentUser === undefined) {
      this.authenticationService.getUserInfoObservable().subscribe(
        data => {
          this.isMe = this.profileId == data.userProfileId;
          this.currentUser = data;
        }
      );
    } else {
      this.isMe = this.profileId == this.currentUser.userProfileId;
    }
    this.viewUserProfile(this.profileId);
  };

  goToMessagePage() {
    this._router.navigate(['/messages', {redirectToChat: this.profileId}]);
  }

  addToFriends() {
    this._searchService.addToFriends(this.profileId).subscribe();
    jQuery('#addToFriends').attr('disabled', 'disabled');
  }

  goToFriendsPage(){
    this._router.navigate(['/friends', {profileId: this.profileId, username: this.userInfo.firstname + ' ' + this.userInfo.lastname}]);
  }

  cancelFriendRequest(){
    this._friendService.removeFriend(this.profileId).subscribe();
    jQuery('#removeFriend').attr('disabled', 'disabled');
  }

  ngAfterViewInit() {
    var that = this;
    if (jQuery(this.cropbox.nativeElement).length > 0) {
      jQuery(this.cropbox.nativeElement).Jcrop({
        aspectRatio: 1,
        onSelect: updateCoords
      }, function () {
        that.jcropApi = this;
      });
    }

    function updateCoords(c) {
      jQuery('#x').val(c.x);
      jQuery('#y').val(c.y);
      jQuery('#w').val(c.w);
      jQuery('#h').val(c.h);
    }
  }

  // change image src for edit icon
  onMouseOver(): void {
    this.editImgSrc = "/app/img/edit_icon_black.png";
  }

  onMouseOut(): void {
    this.editImgSrc = "/app/img/edit_icon_gray.png";
  }


  cropImageAndSave(x, y, w, h) {
    let imgCropData = {
      "x": x,
      "y": y,
      "w": w,
      "h": h
    };
    this.profileService.postCordsImageCrop(imgCropData).subscribe(
      (data) => {
        this.userInfo.cropImgSrc = data.newImageFileName;
        this.viewImageChangeMessage = true;
        setTimeout(function () {
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
      this.showImgSpinner = true;
      this.profileService.uploadProfilePicture(formData)
        .subscribe(
          (data) => {
            this.userInfo.originalImgSrc = data.newImageFileName;
            this.jcropApi.setImage('/api/profile/image/get/' + this.userInfo.originalImgSrc);
            this.showImgSpinner = false;
          }
        );
    }
  }

  editProfile() {
    this._router.navigate(['/edit']);
  }

  doPostCreation(post){
    this.profileService.createUserPost(this.postDTO).subscribe(
      data => {
        if(typeof post.id === "undefined"){
          this.userInfo.posts.unshift(data);
        }
        this.postDTO = {};
      }
    );
  }


  goToProfile(id){
    this._router.navigate(['/profile/'+id]);
    this.viewUserProfile(id);
    this.getProfilePosts(id);
  }

  viewUserProfile(profileId){
    this.profileService.getUserInfo(profileId).subscribe(
        data => {
          this.userInfo = data;
          this.userInfo.postsCount = this.userInfo.posts.length;
          if (this.jcropApi !== undefined) {
            this.jcropApi.setImage('/api/profile/image/get/' + this.userInfo.originalImgSrc);
          }
          this.userInfo.posts.filter(post => post.isShowComments = false);
          this.translate.get('PAGE_TITLES.PROFILE', {username: this.userInfo.firstname + " " + this.userInfo.lastname}).subscribe((res: string) => {
            this.titleService.setTitle(res);
          });
        },
        err => {
          console.log('Something went wrong!');
        }
    );
  }

  deletePost(post){
    this.profileService.deletePost(post.id).subscribe(
      success => {
        let index = this.userInfo.posts.indexOf(post);
        this.userInfo.posts.splice(index, 1);
      }
    );
  }

  selectPostForEditing(post){
    this.postDTO = post;
  }


  createComment(post){
    this.commentDTO.post = post.id;
    this.commentDTO.content = this.commentsContent[post.id];
    this.commentService.createComment(this.commentDTO).subscribe(
      data => {
        post.comments.push(data);
        post.isShowComments = true;
      }
    );
  }

  toggleComments(post){
     if (post.isShowComments) {
       post.isShowComments = false;
     }else {
       if(post.comments.length > 0){
         post.isShowComments = true;
       }
       else{
         this.spinnerShow[post.id] = true;
         this.commentService.getComments(post.id).subscribe(
           data => {
             post.comments = data;
             this.spinnerShow[post.id] = false;
           }
         );
         post.isShowComments = true;
       }
     }
  }

  deleteComment(post, comment){
    this.commentService.deleteComent(comment.id).subscribe(
      success => {
        let commentIndex = post.comments.indexOf(comment);
        post.comments.splice(commentIndex, 1);
      }
    );
  }
  getImageLink(image: string): string {
    if(_.isUndefined(image) || _.isEmpty(image)) {
      return '/app/img/default_user_photo.jpg';
    }
    return '/api/profile/image/get/' + image;
  }

  createLike(post){
    this.likeService.createLike(post.id).subscribe(
      response => {
        if (this.findAlreadyLiked(post)) {
          post.likes = post.likes.filter(like => like.user.userId !== this.currentUser.userProfileId);
        }else
          post.likes.push(response);
      }
    );
  }

  findAlreadyLiked(post){

    if(post.likes.filter(like => like.user.userId == this.currentUser.userProfileId).length > 0){
      return true;
    }
    else return false;
  }
}
