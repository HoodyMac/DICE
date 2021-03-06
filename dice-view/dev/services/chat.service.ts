import {Injectable} from "@angular/core";
import {HttpClient} from "../common/services/http-client.service";
import {Observable} from "rxjs/Observable";
@Injectable()
export class ChatService {

  constructor(private http: HttpClient) {}

  getAllChats(): Observable {
    return this.http.get('api/chats/all').map(res => res.json());
  }

  createChat(friendId: number): Observable {
    return this.http.post('api/chats/' + friendId).map(res => res.json());
  }

  getMessages(chatId: number): Observable {
    return this.http.get('api/chats/messages/' + chatId).map(res => res.json());
  }

  createMessage(message: string, chatId: number): Observable {
    return this.http.postWithOptions('api/chats/messages/' + chatId, message, {
      headers: {
        'Authorization': localStorage.getItem('token')
      }
    }).map(res => res.json());
  }

  refreshMessages(lastAction: number): Observable {
    return this.http.get('api/chats//messages/refresh/' + lastAction).map(res => res.json());
  }
}
