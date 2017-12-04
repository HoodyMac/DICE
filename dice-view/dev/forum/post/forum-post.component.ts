import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ForumService} from "../../services/forum.service";


@Component({
  templateUrl: '../app/forum/post/forum-post.component.html',
  styleUrls: ['../app/css/forum-post.css'],
  providers: [ForumService]
})
export class ForumPostComponent {

  public question = {};

  constructor(private route: ActivatedRoute,
              private forumService: ForumService) {
    this.route.params.subscribe(params => {
      let questionId = params['id'];
      console.log(questionId);
      this.forumService.getQuestion(questionId).subscribe(
        data => this.question = data
      );
    });
  }
}
