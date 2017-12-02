import {Component, AfterViewInit} from '@angular/core';
import {FriendsService} from "../services/friends.service";
import {Router, ActivatedRoute} from '@angular/router';
import {Title} from "@angular/platform-browser";
import {TranslateService} from "ng2-translate";
import {AuthenticationService} from "../common/services/authentication.service";
declare var jQuery: any;

@Component({
  templateUrl: 'dev/friends/friends.component.html',
  styleUrls: ['../app/css/friends.css'],
  providers: [FriendsService]
})
export class FriendsComponent implements AfterViewInit {
  showFriends:boolean = true;
  showFollowers:boolean = false;
  showNewFriends:boolean = false;

  private userFriendsData: [];
  private acceptDone;
  private done;
  private rejectDone;
  private profileId = true;
  private myId;
  private userName = '';

  constructor(private friendsService: FriendsService,
              private _router: Router,
              private titleService: Title,
              private translate: TranslateService,
              private _activeRoute: ActivatedRoute,
              private authService: AuthenticationService) {

      translate.get('PAGE_TITLES.FRIENDS').subscribe((res: string) => {
          this.titleService.setTitle(res);
      });

      this._activeRoute.params.subscribe(params => {
        this.profileId = params['profileId'];
        this.userName = params['username'];
      });

      this.showProfileFriends(this.profileId);
  }

  showMyFriends(){
    this.showFriends = true;
    this.showFollowers = false;
    this.showNewFriends = false;

    var currentUser = this.authService.getUserInfo();
    if(currentUser === undefined) {
      this.authService.getUserInfoObservable().subscribe(
        data => {
          this.userName = data.firstName + ' ' + data.lastName;
        }
      );
    }else
      this.userName = currentUser.firstName + ' ' + currentUser.lastName;

    this.friendsService.getMyFriends().subscribe(
      data => {
        this.userFriendsData = data;
      }
    );
  }

  goToProfile(){
    this._router.navigate(['/profile/'+this.profileId]);
  }

  getFriendsOfFriend(id, friendName){
    this.friendsService.getFriendsByProfileId(id).subscribe(
      data => {
        this.userFriendsData = data;
        this.userName = friendName;
        this.profileId = id;
      }
    );
  }

  acceptFriendsRequest(idUser){
    this.acceptDone = "acceptFriend" + idUser;
    this.done = "done" + idUser;
    this.rejectDone = "rejectFriend" + idUser;
    this.friendsService.acceptFriendsRequest(idUser)
        .subscribe(
            data =>{
              console.log(data);
            }
        );
    console.log(jQuery('#'+this.done).find('hide'));
    jQuery('#'+this.acceptDone).addClass('hide');
    jQuery('#'+this.rejectDone).addClass('hide');
    if(jQuery('#'+this.done).find('hide')){
        jQuery('#'+this.done).removeClass('hide');
        jQuery('#'+this.done).addClass('show');
    }
  }

  showProfileFriends(id){
    this.showFriends = true;
    this.showFollowers = false;
    this.showNewFriends = false;

    this.friendsService.getFriendsByProfileId(id).subscribe(
      data =>{
        this.userFriendsData = data;
      }
    );
  }

  goToMessagePage(id){
    this._router.navigate(['/messages', {redirectToChat: id}]);
  }

  rejectFriendRequest(idUser){
      this.acceptDone = "acceptFriend" + idUser;
      this.done = "done" + idUser;
      this.rejectDone = "rejectFriend" + idUser;
    this.friendsService.rejectFriendRequest(idUser)
        .subscribe(
            data =>{
              console.log(data);
            }
        );
      jQuery('#'+this.acceptDone).addClass('hide');
      jQuery('#'+this.rejectDone).addClass('hide');
      if(jQuery('#'+this.done).find('hide')){
          jQuery('#'+this.done).removeClass('hide');
          jQuery('#'+this.done).addClass('show');
      }
  }

  removeFriend(user){
    this.friendsService.removeFriend(user.id)
        .subscribe(
            data =>{
              let index = this.userFriendsData.indexOf(user);
              this.userFriendsData.splice(index, 1);
            }
        );
  }

  showMyNewFriends(){
    this.showFriends = false;
    this.showFollowers = false;
    this.showNewFriends = true;

    this.friendsService.getUserNewFriendsData()
        .subscribe(
            data =>{
              this.userFriendsData = data;
              console.log(this.userFriendsData);
            }
        );
  }

  showMyFollowers(){
    this.showFriends = false;
    this.showFollowers = true;
    this.showNewFriends = false;
  }

}


