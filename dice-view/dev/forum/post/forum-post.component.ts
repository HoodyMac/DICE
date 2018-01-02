import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ForumService} from "../../services/forum.service";
import {TranslateService} from "ng2-translate";


@Component({
  templateUrl: '../app/forum/post/forum-post.component.html',
  styleUrls: ['../app/css/forum-post.css'],
  providers: [ForumService]
})
export class ForumPostComponent {

  public question = {};

  public answer = {};

  constructor(private route: ActivatedRoute,
              private forumService: ForumService) {
    this.route.params.subscribe(params => {
      let questionId = params['id'];
      console.log(questionId);
      this.forumService.getQuestion(questionId).subscribe(
        data => this.question = data
      );
    });

    console.log(this.question);
  }

  public createAnswer(): void {
    this.forumService.createAnswer(this.answer, this.question.id).subscribe(
      data => {
        this.question.replies.push(data);
        this.answer = {};
      }
    );
  }
}
