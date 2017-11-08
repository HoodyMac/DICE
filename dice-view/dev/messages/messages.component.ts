import {Component} from "@angular/core";
import {ChatService} from "../services/chat.service";
import {FriendsService} from "../services/friends.service";
import {AuthenticationService} from "../common/services/authentication.service";

@Component({
  templateUrl: 'dev/messages/messages.component.html',
  styleUrls: ['../app/css/messages.css'],
  providers: [ChatService, FriendsService]
})
export class MessagesComponent {

  public userInfo: any;
  public selectedChat: any;

  public messages: any[];
  public chats: any[];
  public friends: any[];

  constructor(
    private chatService: ChatService,
    private friendsService: FriendsService,
    private authenticationService: AuthenticationService) {
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

  public selectChat(chat: number) {
    this.userInfo = this.authenticationService.getUserInfo();
    this.selectedChat = chat;
    this.chatService.getMessage(chat.id).subscribe(
      data => {
        this.messages = data;
        this.messages.sort((a, b) => a.createdAt > b.createdAt);
      }
    );
  }

  public createMessage(message: any) {
    this.chatService.createMessage(message, this.selectedChat.id).subscribe(
      data => {
        this.messages.push(data);
        this.messages.sort((a, b) => a.createdAt > b.createdAt);
      }
    );
  }

  public getMessageFloat(senderId: number) {
    if(senderId === this.userInfo.userProfileId) {
      return "right";
    } else {
      return "left";
    }
  }
}
