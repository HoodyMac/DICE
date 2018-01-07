import {Component, OnInit} from "@angular/core";
import {IMultiSelectOption} from "angular-2-dropdown-multiselect";
import {ForumService} from "../../services/forum.service";
import {LikeService} from "../../common/services/like.service";
import {AuthenticationService} from "../../common/services/authentication.service";

@Component({
  templateUrl: '../app/forum/list/forum-list.component.html',
  styleUrls: ['../app/css/forum-list.css'],
  providers: [ForumService, LikeService]
})
export class ForumListComponent {

  public editedQuestion = {};
  public tags: IMultiSelectOption[] = [];
  public questions = [];
  public currentUser = {};

  public  msSettings: IMultiSelectSettings = {
    pullRight: false,
    enableSearch: true,
    checkedStyle: 'fontawesome',
    buttonClasses: 'btn btn-default btn-secondary',
    selectionLimit: 5,
    closeOnSelect: false,
    autoUnselect: false,
    dynamicTitleMaxItems: 5,
    maxHeight: '300px',
    isLazyLoad: true,
    loadViewDistance: 1,
    stopScrollPropagation: true,
    selectAddedValues: true
  };

  constructor(private forumService: ForumService, private likeService: LikeService, private authenticationService: AuthenticationService) {
    this.forumService.getAllTags().subscribe(
      data => this.tags = data.map(item => {
        return {
          'id': item.id,
          'name': item.title
        };
      })
    );

    this.forumService.getAllQuestions().subscribe(
      data => this.questions = data
    );

    this.currentUser = this.authenticationService.getUserInfo();
    if (this.currentUser === undefined) {
      this.authenticationService.getUserInfoObservable().subscribe(
        data => {
          this.currentUser = data;
        }
      );
    }
  }

  public createQuestion() {
    this.forumService.createQuestion(this.editedQuestion).subscribe(
      data => {
        this.editedQuestion = {};
        this.questions.push(data);
      }
    );
  }

  public createLike(post){
    this.likeService.createLike(post.id, 'question').subscribe(
      response => {
        this.likeService.handleLikeCreation(post, this.currentUser.userProfileId, response);
      }
    );
  }
}
