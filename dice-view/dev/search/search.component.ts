import {Component, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
declare var jQuery: any;

@Component({
    templateUrl: 'dev/search/search.component.html',
    styleUrls: ['../app/css/search.css']
})
export class SearchComponent implements AfterViewInit{
    @ViewChild('choseLang') choseLang: ElementRef;
    @ViewChild('choseAgeFrom') choseAgeFrom: ElementRef;
    @ViewChild('choseAgeTo') choseAgeTo: ElementRef;
    ageValues: any = new Array();
    constructor() {
        //generate age array
        for (let i = 1; i <= 100; i++) {
            this.ageValues.push(i);
        }
    }

    ngAfterViewInit() {
        jQuery(this.choseLang.nativeElement).chosen();
        jQuery(this.choseAgeFrom.nativeElement).chosen();
        jQuery(this.choseAgeTo.nativeElement).chosen();
        jQuery('.chosen-container-single').css('width', '70px');
        jQuery('#lang_select > .chosen-container-single').css('width', '90%');
        jQuery('#lang_select > .chosen-container-single').css('margin', '5px 5%');


        jQuery(this.choseAgeFrom.nativeElement).chosen().change(function () {
            jQuery(this.choosenSelect).val();  //your value
        });
        jQuery(this.choseAgeTo.nativeElement).chosen().change(function () {
            jQuery(this.choosenSelect).val();  //your value
        });
        jQuery(this.choseLang.nativeElement).chosen().change(function () {
            jQuery(this.choosenSelect).val();  //your value
        });
    }
}

