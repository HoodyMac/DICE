import {Component} from "@angular/core";
import {ChatService} from "../services/chat.service";
import {FriendsService} from "../services/friends.service";
import {AuthenticationService} from "../common/services/authentication.service";
import {Observable} from "rxjs/Observable";
import {ActivatedRoute } from '@angular/router';

declare var jQuery: any;
@Component({
  templateUrl: 'dev/messages/messages.component.html',
  styleUrls: ['../app/css/messages.css'],
  providers: [ChatService, FriendsService]
})
export class MessagesComponent{
  public viewOptions = {theme: 'vs', readOnly: true};
  public presentedAttachment;

  public editorOptions = {theme: 'vs'};
  public editedCodeBeforSend = {};
  public editedCodeAttachment = {};

  public userInfo: any;
  public selectedChat: any;

  public messages: any[];
  public chats: any[];
  public friends: any[];

  private screenHeight: any;
  private isUploadCode: false;
  private isUploadFile: false;
  private isUploadPhoto: false;

  private isChatCreated = false;
  private redirectFromProfileId;

  constructor(
    private chatService: ChatService,
    private friendsService: FriendsService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute) {
      
    this.route.params.subscribe(params => {
      this.redirectFromProfileId = params['redirectToChat'];
    });

    this.userInfo = this.authenticationService.getUserInfo();
    if(this.userInfo === undefined) {
      this.authenticationService.getUserInfoObservable().subscribe(user => {
        this.userInfo = user;
        this.getAllChats();
      });
    } else {
      console.log(this.userInfo);
      this.getAllChats();
    }
    this.friendsService.getUserFriendsData().subscribe(
      data => this.friends = data
    );
    this.screenHeight = (window.screen.height) - 380;
  }

  public createChat(friendId: number) {
    var chat = this.chats.filter(chat => chat.participantId === friendId);
    if (chat.length === 1) {
      this.selectChat(chat[0]);
    }else {
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
    this.chatService.getMessages(chat.id).subscribe(
      data => {

        this.messages = data;
        this.messages.sort((a, b) => a.createdAt - b.createdAt);
        jQuery('#scroll').scrollTop(jQuery('#scroll')[0].scrollHeight);
      }
    );
  }

  public createMessage(message) {
    if(message['content'] !== null && message['content'].toString().replace(/ /g, "") !== ""){
      if(this.editedCodeAttachment !== {}) {
        message.code = this.editedCodeAttachment;
      }
      this.chatService.createMessage(message, this.selectedChat.id).subscribe(
          data => {
            this.messages.push(data);
            var currentChatIndex = this.chats.indexOf(this.selectedChat);
            this.chats[currentChatIndex].lastAction = data.createdAt;
            this.chats.sort((a, b) => a.lastAction < b.lastAction);
            this.isUploadCode = false;
            this.editedCodeAttachment = {};
          }
      );
    }
    jQuery('#scroll').scrollTop(jQuery('#scroll')[0].scrollHeight);
  }

  public getMessageFloat(senderId: number) {
    // console.log(senderId + ' - ' + this.userInfo.userProfileId);
    if(senderId === this.userInfo.userProfileId) {
      return "right";
    } else {
      return "left";
    }
  }

  public OpenFileExplorer(inputName){
     $('#' + inputName).trigger('click');
  }

  public viewAttachment(attachment: any) {
    this.viewOptions.language = attachment.language.toLowerCase();
    this.viewOptions.value = attachment.code;
    this.presentedAttachment = attachment;
  }

  public languageSelect(language: string) {
    this.editorOptions.language = language.toLowerCase();
    this.editedCodeBeforSend.language = language;
  }

  public clearCodeAttachment() {
    this.editedCodeBeforSend = {};
    this.editorOptions = {theme: 'vs'};
  }

  public saveCodeSnippet(){
      this.isUploadCode = true;
      this.editedCodeAttachment = this.editedCodeBeforSend;
  }
  private getAllChats() {
    this.chatService.getAllChats().subscribe(data => {
      let that: MessagesComponent = this;
      that.chats = data;
      that.chats.sort((a, b) => b.lastAction - a.lastAction);
      if(this.redirectFromProfileId != null){
        var chat = this.chats.filter(chat => chat.participantId == this.redirectFromProfileId);
        if(chat.length != 0){
          this.isChatCreated = true;
          this.selectChat(chat[0]);
        }
      }else if(that.chats.length > 0) {
        that.selectChat(this.chats[0]);
      }
      Observable.interval(5000).subscribe(() => {
        that.chatService.refreshMessages(this.selectedChat.lastAction).subscribe(
          data => {
            data.forEach(message =>{
              if(message.chatId === this.selectedChat.id) {
                that.messages.push(message);
              }
            } );
            that.messages.sort((a, b) => a.createdAt - b.createdAt);
            jQuery('#scroll').scrollTop(jQuery('#scroll')[0].scrollHeight);
          }
        );
      });
    });
  }
}
