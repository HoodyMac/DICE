import {Component} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ForumService} from "../../services/forum.service";
import {LikeService} from "../../common/services/like.service";
import {AuthenticationService} from "../../common/services/authentication.service";

@Component({
  templateUrl: '../app/forum/post/forum-post.component.html',
  styleUrls: ['../app/css/forum-post.css'],
  providers: [ForumService, LikeService]
})
export class ForumPostComponent {

  public question = {likes: []};

  public answer = {};
  public currentUser = {};

  constructor(private route: ActivatedRoute,
              private forumService: ForumService, private likeService: LikeService, private authenticationService: AuthenticationService) {
    this.route.params.subscribe(params => {
      let questionId = params['id'];
      console.log(questionId);
      this.forumService.getQuestion(questionId).subscribe(
        data => this.question = data
      );
    });

    this.currentUser = this.authenticationService.getUserInfo();
    if (this.currentUser === undefined) {
      this.authenticationService.getUserInfoObservable().subscribe(
        (data) => {
          this.currentUser = data;
        }
      );
    }
  }

  public createAnswer(): void {
    this.forumService.createAnswer(this.answer, this.question.id).subscribe(
      data => {
        this.question.replies.push(data);
        this.answer = {};
      }
    );
  }

  createLike(){
    this.likeService.createLike(this.question.id, 'question').subscribe(
      response => {
        this.likeService.handleLikeCreation(this.question, this.currentUser.userProfileId, response);
      }
    );
  }
}
