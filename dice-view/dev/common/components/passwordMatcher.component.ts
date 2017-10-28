import {NG_VALIDATORS, AbstractControl} from "@angular/forms";
import {Directive} from "@angular/core";


@Directive({
    selector: '[password-matcher]',
    providers: [{provide: NG_VALIDATORS, multi: true, useValue: passwordMatcher},]
})

export class PasswordMatcher{

}

function passwordMatcher(c: AbstractControl){
    if(!c.get('password') || !c.get('confirmPassword')) return null;
    return c.get('password').value === c.get('confirmPassword').value ? null : {'nomatch' : true};
}