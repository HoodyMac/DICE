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
}
