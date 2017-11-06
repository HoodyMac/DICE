import {Component} from '@angular/core';
import {ChatService} from "../services/chat.service";

@Component({
  templateUrl: 'dev/messages/messages.component.html',
  styleUrls: ['../app/css/messages.css'],
  providers: [ChatService]
})

export class MessagesComponent {

  public chats: any[];
  public friends = [{
    id: 14,
    firstName: 'Roman',
    lastName: 'Mysan'
  }];

  constructor(private chatService: ChatService) {
    this.chatService.getAllChats().subscribe(data => {
      this.chats = data;
      this.chats.sort((a, b) => a.lastAction < b.lastAction);
    });
    // todo get list of friends from api
  };

  public createChat(friendId: number) {
    this.chatService.createChat(friendId).subscribe(
      data => this.chats.unshift(data)
    )
  }
}
