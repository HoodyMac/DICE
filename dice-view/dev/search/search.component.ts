import {Component, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import {SearchService} from '../services/search.service'
import {FormGroup, FormBuilder, NgForm} from "@angular/forms";
import {ActivatedRoute} from '@angular/router';
import {TranslateService} from "ng2-translate";
import {Title} from "@angular/platform-browser";
declare var jQuery: any;

@Component({
    templateUrl: 'dev/search/search.component.html',
    styleUrls: ['../app/css/search.css'],
    providers: [SearchService, NgForm]
})

export class SearchComponent implements AfterViewInit{
    @ViewChild('choseLang') choseLang: ElementRef;
    @ViewChild('choseAgeFrom') choseAgeFrom: ElementRef;
    @ViewChild('choseAgeTo') choseAgeTo: ElementRef;
    ageValues: any = new Array();


    searchData = [];
    public searchForm: FormGroup;
    public fullname: "";
    private buttonDisabled;

    constructor(private searchService: SearchService,
                private fb:FormBuilder,
                private activatedRoute: ActivatedRoute,
                private titleService: Title,
                private translate: TranslateService) {

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
            this.ageValues.push(i);
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
        jQuery(this.choseAgeFrom.nativeElement).chosen();
        jQuery(this.choseAgeTo.nativeElement).chosen();

        jQuery('.chosen-container-single').css('width', '70px');
        jQuery('#lang_select > .chosen-container-single').css('width', '90%');
        jQuery('#lang_select > .chosen-container-single').css('margin', '5px 5%');

        jQuery(this.choseAgeFrom.nativeElement).chosen().change(function () {
            mainClass.getUsersData(searchForm.value);
        });
        jQuery(this.choseAgeTo.nativeElement).chosen().change(function () {
            mainClass.getUsersData(searchForm.value);
        });
        jQuery(this.choseLang.nativeElement).chosen().change(function () {
            mainClass.getUsersData(searchForm.value);
        });

        this.getUsersData();
    }

    getUsersData(){
        let searchData = {};
        searchData['ageFrom'] = jQuery(this.choseAgeFrom.nativeElement).val();
        searchData['ageTo'] = jQuery(this.choseAgeTo.nativeElement).val();
        searchData['programmingLanguages'] = jQuery(this.choseLang.nativeElement).val();
        searchData['gender'] =  this.searchForm.controls['gender'].value;
        searchData['city'] = this.searchForm.controls['city'].value;
        searchData['fullName'] = this.searchForm.controls['fullName'].value.trim();
        searchData['gender'] = this.searchForm.controls['gender'].value;
        console.log(this.searchForm.controls['gender'].value);
        console.log(searchData);

        this.searchService.getUserSearchData(searchData)
            .subscribe(
                data =>{
                    this.searchData = data;
                    console.log(this.searchData);
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
