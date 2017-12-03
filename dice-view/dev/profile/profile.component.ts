import {Component, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import {ProfileService} from "../services/profile.service";
import {Router, ActivatedRoute} from '@angular/router';
import {SearchService} from '../services/search.service';
import {FriendsService} from '../services/friends.service';
import {AuthenticationService} from "../common/services/authentication.service";
import {CommentService} from "../common/services/comment.service";
import {TranslateService} from "ng2-translate";
import {Title} from "@angular/platform-browser";

let clicked = true;
declare var jQuery: any;

@Component({
  templateUrl: 'dev/profile/profile.component.html',
  styleUrls: ['../app/css/profile.css'],
  providers: [ProfileService, SearchService, FriendsService, CommentService]
})

export class ProfileComponent implements AfterViewInit {
  @ViewChild('fileInput') inputEl: ElementRef;

  countModules: any; //count_module[];
  userInfo = {};
  editImgSrc: string = "/app/img/edit_icon_gray.png";
  profileId;
  isMe: boolean;
  profilePosts :any = [];
  postDTO = {};
  inPostEdit: boolean = false;
  postToEdit = {};
  commentDTO = {post: null, content: ''};

  @ViewChild('cropbox') cropbox: ElementRef;

  private jcropApi: any;
  viewImageChangeMessage: boolean = false;


  constructor(private profileService: ProfileService,
              private authenticationService: AuthenticationService,
              private _router: Router, private route: ActivatedRoute, private _searchService: SearchService,
              private _friendService: FriendsService,
              private titleService: Title,
              private translate: TranslateService,
              private commentService: CommentService) {

    this.route.params.subscribe(params => {
      this.profileId = params['id'];
    });

    this.getProfilePosts();

    var currentUser = this.authenticationService.getUserInfo();
    if (currentUser === undefined) {
      this.authenticationService.getUserInfoObservable().subscribe(
        data => this.isMe = this.profileId == data.userProfileId
      );
    } else {
      this.isMe = this.profileId == currentUser.userProfileId;
    }

    this.profileService.getUserInfo(this.profileId).subscribe(
      data => {
        this.userInfo = data;
        if (this.jcropApi !== undefined) {
          this.jcropApi.setImage('/api/profile/image/get/' + this.userInfo.originalImgSrc);
        }
        translate.get('PAGE_TITLES.PROFILE', {username: this.userInfo.firstname + " " + this.userInfo.lastname}).subscribe((res: string) => {
          this.titleService.setTitle(res);
        });
      },
      err => {
        console.log('Something went wrong!');
      }
    );
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

  getProfilePosts(){
    this.profileService.getPosts(this.profileId).subscribe(
      data => {
        this.profilePosts = data;
      }
    );
  }

  doPostCreation(){
    this.profileService.createUserPost(this.postDTO).subscribe(
      data => {
        this.profilePosts.unshift(data);
      }
    );
  }

  goToProfile(id){
    this._router.navigate(['/profile/'+id]);
  }

  deletePost(post){
    this.profileService.deletePost(post.id).subscribe(
      success => {
        let index = this.profilePosts.indexOf(post);
        this.profilePosts.splice(index, 1);
      }
    );
  }

  selectPostForEditing(post){
    this.postToEdit = post;
  }

  editPost(){
    //TODO
  }

  createComment(post){
    this.commentDTO.post = post.id;
    this.commentService.createComment(this.commentDTO).subscribe(
      data => {
        post.comments.push(data);
      }
    );
  }

  deleteComment(post, comment){
    this.commentService.deleteComent(comment.id).subscribe(
      success => {
        let commentIndex = post.comments.indexOf(comment);
        post.comments.splice(commentIndex, 1);
      }
    );
  }
}
