import {Component} from '@angular/core';

@Component({
  templateUrl: 'dev/friends/friends.component.html',
  styleUrls: ['../app/css/friends.css']
})
export class FriendsComponent {
  showFriends:boolean = true;
  showFollowers:boolean = false;

  showMyFriends(){
    this.showFriends = true;
    this.showFollowers = false;
  }
  showMyFollowers(){
    this.showFriends = false;
    this.showFollowers = true;
  }
}


