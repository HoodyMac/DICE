import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "../common/services/http-client.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ForumService {

  constructor(private http: HttpClient) {}

  createQuestion(question): Observable {
    return this.http.post('/api/forum/question', question).map(res => res.json());
  }

  getAllQuestions(): Observable {
    return this.http.get('/api/forum/question').map(res => res.json());
  }

  getQuestion(id: number): Observable {
    return this.http.get('/api/forum/question/' + id).map(res => res.json());
  }

  getAllTags(): Observable {
    return this.http.get('/api/forum/tags').map(res => res.json());
  }
}
