import {Component} from '@angular/core';
import {FriendsService} from "../services/friends.service";

@Component({
  templateUrl: 'dev/friends/friends.component.html',
  styleUrls: ['../app/css/friends.css'],
  providers: [FriendsService]
})
export class FriendsComponent {
  showFriends:boolean = true;
  showFollowers:boolean = false;
  showNewFriends:boolean = false;

  private userFriendsData: [];

  constructor(private friendsService: FriendsService) {

    this.showMyFriends();
  }

  acceptFriendsRequest(idUser){

    this.friendsService.acceptFriendsRequest(idUser)
        .subscribe(
            data =>{
              console.log(data);
            }
        );
  }

  rejectFriendRequest(idUser){
    this.friendsService.rejectFriendRequest(idUser)
        .subscribe(
            data =>{
              console.log(data);
            }
        );
  }

  removeFriend(idUser){
    this.friendsService.removeFriend(idUser)
        .subscribe(
            data =>{
              console.log(data);
            }
        );
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


