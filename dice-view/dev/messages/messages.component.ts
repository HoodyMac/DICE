import {Component} from "@angular/core";
import {ChatService} from "../services/chat.service";
import {FriendsService} from "../services/friends.service";
import {AuthenticationService} from "../common/services/authentication.service";
import {Observable} from "rxjs/Observable";
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from "ng2-translate";
import {Title} from "@angular/platform-browser";

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
  public editedCodeBeforeSend = {};
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

  private redirectFromProfileId;
  private activeChat: "";

  constructor(
    private chatService: ChatService,
    private friendsService: FriendsService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,
    private titleService: Title,
    private router: Router,
    private translate: TranslateService) {

    translate.get('PAGE_TITLES.MESSAGES').subscribe((res: string) => {
      this.titleService.setTitle(res);
    });

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
      this.getAllChats();
    }
    this.friendsService.getUserFriendsData().subscribe(
      data => this.friends = data
    );
    this.screenHeight = (window.screen.height) - 380;
  }

  public createChat(friendId: number) {
    var chat = this.chats.filter(chat => chat.participantId == friendId);
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
    console.log(this.selectedChat);
    this.chatService.getMessages(chat.id).subscribe(
      data => {

        this.messages = data;
        this.messages.sort((a, b) => a.createdAt - b.createdAt);
        jQuery('#scroll').scrollTop(jQuery('#scroll')[0].scrollHeight);
        this.activeChat = this.selectedChat.id;
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
            this.selectedChat.lastAction = data.createdAt;
            this.selectedChat.lastMessage = data.content;
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
  }

  public clearCodeAttachment() {
    this.editedCodeBeforeSend = {};
    this.editorOptions = {theme: 'vs'};
  }

  public saveCodeSnippet(){
      this.isUploadCode = true;
      this.editedCodeAttachment = this.editedCodeBeforeSend;
  }

  public gotoProfile(id: number) {
    this.router.navigate(['/profile', id]);
  }

  private getAllChats() {
    this.chatService.getAllChats().subscribe(data => {
      let that: MessagesComponent = this;
      that.chats = data;
      that.chats.sort((a, b) => b.lastAction - a.lastAction);
      if(this.redirectFromProfileId){
        this.createChat(this.redirectFromProfileId);
      }else if(that.chats.length > 0) {
        that.selectChat(this.chats[0]);
      }
      Observable.interval(5000).subscribe(() => {
        if(this.selectedChat !== undefined) {
          that.chatService.refreshMessages(this.selectedChat.lastAction).subscribe(
            data => {
              data.forEach(message =>{
                if(message.chatId === this.selectedChat.id) {
                  that.messages.push(message);
                }
              } );
              that.messages.sort((a, b) => a.createdAt - b.createdAt);
              if(data.length !== 0) {
                let lastIndex = this.messages.length - 1;
                this.selectedChat.lastAction = this.messages[lastIndex].createdAt;
                this.selectedChat.lastMessage = this.messages[lastIndex].content;
                jQuery('#scroll').scrollTop(jQuery('#scroll')[0].scrollHeight);
              }
            }
          );
        }
      });
    });
  }
}
