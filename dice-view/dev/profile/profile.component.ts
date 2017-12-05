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
  showComments = false;
  currentUser = {};

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
  }

  goToFriendsPage(){
    this._router.navigate(['/friends', {profileId: this.profileId, username: this.userInfo.firstname + ' ' + this.userInfo.lastname}]);
  }

  cancelFriendRequest(){
    this._friendService.removeFriend(this.profileId).subscribe();
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
      this.profileService.uploadProfilePicture(formData)
        .subscribe(
          (data) => {
            this.userInfo.originalImgSrc = data.newImageFileName;
            this.jcropApi.setImage('/api/profile/image/get/' + this.userInfo.originalImgSrc);
          }
        );
    }
  }

  editProfile() {
    this._router.navigate(['/edit']);
  }

  doPostCreation(){
    this.profileService.createUserPost(this.postDTO).subscribe(
      data => {
        this.userInfo.posts.unshift(data);
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
          if (this.jcropApi !== undefined) {
            this.jcropApi.setImage('/api/profile/image/get/' + this.userInfo.originalImgSrc);
          }
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
    console.log(this.postDTO);
  }


  createComment(post){
    this.commentDTO.post = post.id;
    this.commentService.createComment(this.commentDTO).subscribe(
      data => {
        post.comments.push(data);
      }
    );
  }

  toggleComments(post){
     if (this.showComments) {
       this.showComments = false;
     }else {
        this.commentService.getComments(post.id).subscribe(
          data => {
           post.comments = data;
          }
        );
       this.showComments = true;
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
        if (this.findAlreadyLiked(post).length > 0) {
          post.likes = post.likes.filter(like => like.user.userId !== this.currentUser.userProfileId);
        }else
          post.likes.push(response);
      }
    );
  }

  findAlreadyLiked(post){
    return post.likes.filter(like => like.user.userId == this.currentUser.userProfileId);
  }
}
