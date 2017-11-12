import {Component, AfterViewInit} from '@angular/core';
import {FriendsService} from "../services/friends.service";
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

  constructor(private friendsService: FriendsService) {

    this.showMyFriends();
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

  removeFriend(idUser){
    this.friendsService.removeFriend(idUser)
        .subscribe(
            data =>{
              console.log(data);
            }
        );
      this.showMyFriends();
  }

  showMyFriends(){
    this.showFriends = true;
    this.showFollowers = false;
    this.showNewFriends = false;

    this.friendsService.getUserFriendsData()
        .subscribe(
            data =>{
              this.userFriendsData = data;
              console.log(this.userFriendsData);
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


