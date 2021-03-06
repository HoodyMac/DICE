import {Component, ElementRef, ViewChild} from "@angular/core";
import {ChatService} from "../services/chat.service";
import {FriendsService} from "../services/friends.service";
import {AuthenticationService} from "../common/services/authentication.service";
import {Observable} from "rxjs/Observable";
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from "ng2-translate";
import {Title} from "@angular/platform-browser";
import _ from "lodash";

declare var jQuery: any;

@Component({
  templateUrl: 'dev/messages/messages.component.html',
  styleUrls: ['../app/css/messages.css'],
  providers: [ChatService, FriendsService]
})
export class MessagesComponent{
  @ViewChild('fileInput') inputEl: ElementRef;
  @ViewChild('imgInput') inputImg: ElementRef;

  public viewOptions = {theme: 'vs', readOnly: true};
  public presentedAttachment;

  public editorOptions = {theme: 'vs'};
  public editedCodeBeforeSend = {};
  public editedCodeAttachment = {};

  public userInfo: any;
  public selectedChat: any;

  public messages: any[] = [];
  public chats: any[] = [];
  public friends: any[] = [];

  private screenHeight: any;
  private isUploadCode: false;
  private isUploadFile: false;
  private isUploadPhoto: false;

  private files: any[] = [];

  private redirectFromProfileId;
  private activeChat: "";
  private filteredChats: any;
  private filteredFriends: any;

  public fileAccept = ".xlsx,.xls,.doc, .docx,.ppt, .pptx,.txt,.pdf, .zip, .rar, .7z, .csv, .json, .php, .less, .css, .ts, .js, .html, .java, .xml";
  public imgAccept = "image/*";

  constructor(
    private chatService: ChatService,
    private friendsService: FriendsService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,
    private titleService: Title,
    private router: Router,
    private translate: TranslateService) {

    console.log(this.fileAccept);
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
      data => this.filteredFriends = this.friends = data
    );
    this.screenHeight = (window.screen.height);
  }

  public createChat(friendId: number) {
    let chat = this.chats.filter(chat => chat.participantId === friendId);
    if (chat.length === 1) {
      this.selectChat(chat[0]);
    } else {
      this.chatService.createChat(friendId).subscribe(
        data => {
          this.chats.unshift(data);
          this.filteredChats = _.cloneDeep(this.chats);
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
        this.activeChat = this.selectedChat.id;
        jQuery('#scroll').scrollTop(jQuery('#scroll')[0].scrollHeight);
        $('html, body').animate({scrollTop:$(document).height()}, 'slow');
      }
    );

  }

  public createMessage(message) {
    if(message['content'] !== null && message['content'].toString().replace(/ /g, "") !== ""){
      if(!_.isEmpty(this.editedCodeAttachment)) {
        message.code = this.editedCodeAttachment;
      }
      let formData = new FormData();
      if (this.files.length > 0) { // a file was selected
        for (let i = 0; i < this.files.length; i++) {
          formData.append('files[]', this.files[i]);
        }
      }
      formData.append('message', JSON.stringify(message));
      this.chatService.createMessage(formData, this.selectedChat.id).subscribe(
        data => {
            this.messages.push(data);
            var currentChatIndex = this.chats.indexOf(this.selectedChat);
            this.chats[currentChatIndex].lastAction = data.createdAt;
            this.chats.sort((a, b) => a.lastAction < b.lastAction);
            this.filteredChats = _.cloneDeep(this.chats);
            this.isUploadCode = false;
            this.editedCodeAttachment = {};
            this.selectedChat.lastAction = data.createdAt;
            this.selectedChat.lastMessage = data.content;
            this.files = [];
          }
      );
    }
    $('html, body').animate({scrollTop:$(document).height()}, 'slow');
    jQuery('#scroll').scrollTop(jQuery('#scroll')[0].scrollHeight);
  }

  public selectFile() {
    let inputEl: HTMLInputElement = this.inputEl.nativeElement;
    let fileCount: number = inputEl.files.length;
    if (fileCount > 0) {
      for (let i = 0; i < fileCount; i++) {
        this.files.push(inputEl.files.item(i));
        console.log(this.files);
      }
    }
  }

  public selectImages() {
    let inputImg: HTMLInputElement = this.inputImg.nativeElement;
    let imgCount: number = inputImg.files.length;
    if (imgCount > 0) {
      for (let i = 0; i < imgCount; i++) {
        this.files.push(inputImg.files.item(i));
        console.log(this.files);
      }
    }
  }

  public getMessageFloat(senderId: number) {
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
      that.filteredChats = that.chats;
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

  public deleteFile(file){
    let index = this.files.indexOf(file);
    this.files.splice(index, 1);
  }

  public deleteCode(){
    this.isUploadCode = false;
    this.editedCodeAttachment = {};
  }

  public filterChats(value){
    value = value.replace(/[\s]/g, '');
    if(!value) this.filteredChats = _.cloneDeep(this.chats) ;
    this.filteredChats = _.cloneDeep(this.chats).filter(item => item.name.replace(/[\s]/g, '').toLowerCase().indexOf(value.toLowerCase()) > -1);
  }

  public filterFriends(value){
    value = value.replace(/[\s]/g, '');
    if(!value) this.filteredFriends = _.cloneDeep(this.friends);
    this.friends.forEach(item => item['fullName'] = item.firstName + " " + item.lastName);
    this.filteredFriends = _.cloneDeep(this.friends).filter(item => item.fullName.replace(/[\s]/g, '').toLowerCase().indexOf(value.toLowerCase()) > -1);
  }
}
