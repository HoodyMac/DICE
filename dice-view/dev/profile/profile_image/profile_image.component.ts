import {Component} from '@angular/core';
let clicked = true;
@Component({
    selector: 'profile_image',
    templateUrl: 'app/profile/profile_image/profile_image.component.html',
    styleUrls: ['app/profile/profile_image/profile_image.css']
})
export class ProfileImageComponent {

    toggle(id): void{
        let modal = document.getElementById(id);
        if(clicked) {
            modal.style.display = 'block';
            clicked = false;
        }
        else {
            modal.style.display = 'none';
            clicked = true;
        }
    }
}
