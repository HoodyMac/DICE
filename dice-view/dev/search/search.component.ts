import {Component, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import {SearchService} from '../services/search.service'
import {FormGroup, FormBuilder, NgForm} from "@angular/forms";
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from "ng2-translate";
import {Title} from "@angular/platform-browser";
import {FriendsService} from "../services/friends.service";
declare var jQuery: any;

@Component({
    templateUrl: 'dev/search/search.component.html',
    styleUrls: ['../app/css/search.css'],
    providers: [SearchService, NgForm]
})

export class SearchComponent implements AfterViewInit{
    @ViewChild('choseLang') choseLang: ElementRef;
    ageFromValues: any = new Array();
    ageToValues: any = new Array();

    searchData = [];
    public searchForm: FormGroup;
    public fullname: "";
    private buttonDisabled;
    private spinnerShow = false;

    constructor(private searchService: SearchService,
                private fb:FormBuilder,
                private activatedRoute: ActivatedRoute,
                private titleService: Title,
                private translate: TranslateService,
                private _router: Router,
                private route: ActivatedRoute) {

        translate.get('PAGE_TITLES.SEARCH').subscribe((res: string) => {
            this.titleService.setTitle(res);
        });

        this.activatedRoute.params.subscribe(
            data =>{
                    this.fullname = data['fullname'];
            }
        );


        //generate age value
        for (let i = 1; i <= 100; i++) {
            this.ageFromValues.push(i);
        }
        for (let i = 1; i <= 100; i++) {
            this.ageToValues.push(i);
        }

        this.searchForm = fb.group({
            city: '',
            ageFrom: '',
            ageTo: '',
            gender: 'NULL',
            isOnline: '',
            programmingLanguages: '',
            fullName: ''
        });

    }

    ngAfterViewInit() {
        let mainClass = this;
        let searchForm = this.searchForm;
        jQuery(this.choseLang.nativeElement).chosen();

        jQuery('.chosen-container-single').css('width', '70px');
        jQuery('#lang_select > .chosen-container-single').css('width', '90%');
        jQuery('#lang_select > .chosen-container-single').css('margin', '5px 5%');

        jQuery(this.choseLang.nativeElement).chosen().change(function () {
            mainClass.getUsersData();
        });

        this.getUsersData();
    }

     goToFriendsPage(userId, username){
      this._router.navigate(['/friends', {profileId: userId, username: username}]);
    }

    setAgeTo(minAgeValue){
        this.ageToValues = [];
        if(minAgeValue != ""){
            for (let i = minAgeValue; i <= 100; i++) {
                this.ageToValues.push(i);
            }
        }
        else{
            for (let i = 1; i <= 100; i++) {
                this.ageToValues.push(i);
            }
        }
    }
    setAgeFrom(maxAgeValue){
        this.ageFromValues = [];
        if(maxAgeValue != ""){
            for (let i = 1; i <= maxAgeValue; i++) {
                this.ageFromValues.push(i);
            }
        }
        else{
            for (let i = 1; i <= 100; i++) {
                this.ageFromValues.push(i);
            }
        }
    }

    getUsersData(){
        let searchData = {};
        searchData['programmingLanguages'] = jQuery(this.choseLang.nativeElement).val();
        searchData['gender'] =  this.searchForm.controls['gender'].value;
        searchData['city'] = this.searchForm.controls['city'].value.trim();
        searchData['fullName'] = this.searchForm.controls['fullName'].value.trim();
        searchData['gender'] = this.searchForm.controls['gender'].value;
        searchData['ageFrom'] = this.searchForm.controls['ageFrom'].value;
        searchData['ageTo'] = this.searchForm.controls['ageTo'].value;
        console.log(searchData);

        this.spinnerShow = true;
        this.searchService.getUserSearchData(searchData)
            .subscribe(
                data =>{
                    this.searchData = data;
                    this.spinnerShow = false;
                }
            );
    }

    addUserToFriends(iUser){
        this.buttonDisabled = "addToFriends"+iUser;
        this.searchService.addToFriends(iUser)
            .subscribe(
                data =>{
                    data.friendShipStatus = 'SENT';
                }
            );
        jQuery('#'+this.buttonDisabled).attr('disabled', 'disabled');
    }
}
