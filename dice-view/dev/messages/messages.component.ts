import {Component} from "@angular/core";
import {ChatService} from "../services/chat.service";
import {FriendsService} from "../services/friends.service";
import {AuthenticationService} from "../common/services/authentication.service";
import {Observable} from "rxjs/Observable";

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
    let that: MessagesComponent = this;

    this.authenticationService.getUserInfoObservable().subscribe(user => this.userInfo = user);
    this.chatService.getAllChats().subscribe(data => {
      this.chats = data;
      this.chats.sort((a, b) => b.lastAction - a.lastAction);
      if(this.chats.length > 0) {
        this.selectChat(this.chats[0]);
      }
      Observable.interval(5000).subscribe(() => {
        that.chatService.refreshMessages(this.selectedChat.lastAction).subscribe(
          data => {
            data.forEach(message =>{
              if(message.chatId === this.selectedChat.id) {
                this.messages.push(message);
              }
            } );
            this.messages.sort((a, b) => a.createdAt - b.createdAt);
          }
        );
      });
    });
    this.friendsService.getUserFriendsData().subscribe(
      data => this.friends = data
    );
    this.screenHeight = (window.screen.height) - 390 + "px";
  };

  public createChat(friendId: number) {
    var chat = this.chats.filter(chat => chat.participantId === friendId);
    if (chat.length === 1) {
      this.selectChat(chat[0]);
    } else {
      this.chatService.createChat(friendId).subscribe(
        data => {
          this.chats.unshift(data);
          this.selectChat(data);
        }
      );
    }
  }

  public selectChat(chat: any) {
    this.selectedChat = chat;
    this.chatService.getMessage(chat.id).subscribe(
      data => {
        this.messages = data;
        this.messages.sort((a, b) => a.createdAt - b.createdAt);
        jQuery('#scroll').scrollTop(jQuery('#scroll')[0].scrollHeight);
      }
    );
}

  public createMessage(message: string) {
    if(message['content'] !== null && message['content'].toString().replace(/ /g, "") !== ""){
      this.chatService.createMessage(message, this.selectedChat.id).subscribe(
          data => {
            this.messages.push(data);
            var currentChatIndex = this.chats.indexOf(this.selectedChat);
            this.chats[currentChatIndex].lastAction = data.createdAt;
            this.chats.sort((a, b) => a.lastAction < b.lastAction);
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

  public OpenImgUpload(){
     $('#imgupload').trigger('click');
  }
}
