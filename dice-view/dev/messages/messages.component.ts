import {Component} from '@angular/core';
import {ChatService} from "../services/chat.service";
import {FriendsService} from "../services/friends.service";

@Component({
  templateUrl: 'dev/messages/messages.component.html',
  styleUrls: ['../app/css/messages.css'],
  providers: [ChatService, FriendsService]
})

export class MessagesComponent {

  public chats: any[];
  public friends: any[];

  constructor(
    private chatService: ChatService,
    private friendsService: FriendsService) {
    this.chatService.getAllChats().subscribe(data => {
      this.chats = data;
      this.chats.sort((a, b) => a.lastAction < b.lastAction);
    });
    this.friendsService.getUserFriendsData().subscribe(
      data => this.friends = data
    );
  };

  public createChat(friendId: number) {
    this.chatService.createChat(friendId).subscribe(
      data => this.chats.unshift(data)
    )
  }
}
