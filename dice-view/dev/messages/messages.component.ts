import {Component} from "@angular/core";
import {ChatService} from "../services/chat.service";
import {FriendsService} from "../services/friends.service";
import {AuthenticationService} from "../common/services/authentication.service";

declare var jQuery: any;
@Component({
  templateUrl: 'dev/messages/messages.component.html',
  styleUrls: ['../app/css/messages.css'],
  providers: [ChatService, FriendsService]
})
export class MessagesComponent{
  public userInfo: any;
  public selectedChat: any;

  public messages: any[];
  public chats: any[];
  public friends: any[];

  private screenHeight: any;

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

    this.screenHeight = (window.screen.height) - 390 + "px";
  };

  public createChat(friendId: number) {
    var chat = this.chats.filter(chat => chat.participantId === friendId);
    if (chat.length === 1) {
      this.selectedChat = chat[0];
    } else {
      this.chatService.createChat(friendId).subscribe(
        data => {
          this.chats.unshift(data);
          this.selectedChat = data;
        }
      );
    }
  }

  public selectChat(chat: number) {
    this.userInfo = this.authenticationService.getUserInfo();
    this.selectedChat = chat;
    this.chatService.getMessage(chat.id).subscribe(
      data => {
        this.messages = data;
        this.messages.sort((a, b) => a.createdAt > b.createdAt);
        jQuery('#scroll').scrollTop(jQuery('#scroll')[0].scrollHeight);
      }
    );
}

  public createMessage(message: string) {
    if(message['content'] !== null && message['content'].toString().replace(/ /g, "") !== ""){
      this.chatService.createMessage(message, this.selectedChat.id).subscribe(
          data => {
            this.messages.push(data);
            jQuery('#scroll').scrollTop(jQuery('#scroll')[0].scrollHeight);
          }
      );
    }
  }

  public getMessageFloat(senderId: number) {
    if(senderId === this.userInfo.userProfileId) {
      return "right";
    } else {
      return "left";
    }
  }
}
