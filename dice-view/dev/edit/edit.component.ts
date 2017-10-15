import {Component, ViewChild, ElementRef, AfterViewInit, Input, Output, EventEmitter} from '@angular/core';
import {EditService} from "../services/edit.service";
import {AuthenticationService} from "../common/services/authentication.service";
import {ActivatedRoute} from '@angular/router';
let clicked = true;
declare var jQuery: any;

@Component({
    templateUrl: 'dev/edit/edit.component.html',
    styleUrls: ['../app/css/edit.css'],
    providers: [EditService]
})
export class EditComponent implements AfterViewInit{
    @ViewChild('choosenSelect') choosenSelect: ElementRef;
    showEdit:boolean = true;
    showGeneral:boolean = false;
    editUserPass: Object;
    userBasicInfo = {};
    userGeneralInfo = {};
    userPass: Object;
    progLang: Object;
    userId: Number;

    constructor(
        private editService: EditService,
        private activatedRoute: ActivatedRoute) {

        this.activatedRoute.params.subscribe(
            data =>{
                this.userId = data['user'];
            }
        );

        this.editService.getUserBasicInfo(this.userId).subscribe(value => {
                this.userBasicInfo = value;
                console.log(this.userBasicInfo);
            },
            err => {
                console.log('Something went wrong!');
            }
        );



        // this.editService.getUserGeneralInfo("server_url").subscribe(value => {
        //         this.userGeneralInfo = value;
        //     },
        //     err => {
        //         console.log('Something went wrong!');
        //     }
        // );
        //
        //
        // this.editService.getPass("server_url").subscribe(value => {
        //         this.userPass = value;
        //     },
        //     err => {
        //         console.log('Something went wrong!');
        //     }
        // );


    }

    saveUserBasicInfo(){
        this.progLang = jQuery(this.choosenSelect.nativeElement).val();
        //this.userBasicInfo['programmingLanguages'] = this.progLang;
        this.editService.setUserBasicInfo(this.userBasicInfo, this.userId)
        .subscribe(
            data =>{
                this.userBasicInfo = data;
            }
        );
    }

    saveUserGeneralInfo(editGeneralData: Object){
        this.editService.setUserGeneralInfo(editGeneralData, "server_url");
    }
    saveUserPassword(editUserPass){
        this.editService.setUserPassword(editUserPass, "server_url");
    }

    // jQuery Chosen initialize...
    ngAfterViewInit() {
        if( jQuery(this.choosenSelect.nativeElement).length > 0){
            jQuery(this.choosenSelect.nativeElement).chosen();
        }
    }

    // toggle main content for 'Edit' page
    showEditSettings(){
        this.showEdit = true;
        this.showGeneral = false;
    }
    showGeneralSettings(){
        this.showEdit = false;
        this.showGeneral = true;
    }

    // open and close modal window
    toggle(id): void {
        let modal = document.getElementById(id);
        if (clicked) {
            modal.style.display = 'block';
            clicked = false;
        }
        else {
            modal.style.display = 'none';
            clicked = true;
        }
    }
}
