import {Component, ViewChild, ElementRef, AfterViewInit, Input, Output, EventEmitter} from '@angular/core';
let clicked = true;
declare var jQuery: any;

@Component({
    templateUrl: 'dev/edit/edit.component.html',
    styleUrls: ['../app/css/edit.css']
})
export class EditComponent implements AfterViewInit{
    @ViewChild('choosenSelect') choosenSelect: ElementRef;
    showEdit:boolean = true;
    showGeneral:boolean = false;

    // jQuery Chosen initialize...
    ngAfterViewInit() {
        if( jQuery(this.choosenSelect.nativeElement).length > 0){
            jQuery(this.choosenSelect.nativeElement).chosen();
            jQuery(this.choosenSelect.nativeElement).chosen().change(function () {
                jQuery(this.choosenSelect).val();  //your value
            });
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


