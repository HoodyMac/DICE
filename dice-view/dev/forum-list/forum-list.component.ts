import {Component, OnInit} from "@angular/core";
import {IMultiSelectOption} from "angular-2-dropdown-multiselect";
import {ForumService} from "../services/forum.service";

@Component({
  templateUrl: '../app/forum-list/forum-list.component.html',
  styleUrls: ['../app/css/forum-list.css'],
  providers: [ForumService]
})
export class ForumListComponent {

  public editedQuestion = {};

  public selectedTags: number[];
  public tags: IMultiSelectOption[] = [];

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

  constructor(private forumService: ForumService) {
    this.forumService.getAllTags().subscribe(
      data => this.tags = data.map(item => {
        return {
          'id': item.id,
          'name': item.title
        };
      })
    );
  }

  public createQuestion() {
    console.log(this.editedQuestion);
    this.forumService.createQuestion(this.editedQuestion).subscribe(
      data => {
        console.log(data);
        this.editedQuestion = {};
      }
    );
  }
}
