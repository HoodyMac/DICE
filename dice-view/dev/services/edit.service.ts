import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "../common/services/http-client.service";

@Injectable()
export class EditService{

    constructor(private http: HttpClient){
        console.log('PostService Initialized...');

    }
    getUserBasicInfo(url){
        /**
         {
            firstName: string;
            lastName: string;
            gender:string;
            birthday:string;
            city:string;
            education:string;
            work:string;
            prgLanguages:string;

        }
         */
        return this.http.get(url).map(res => res.json());
    }

    getUserGeneralInfo(url){

            /**
             {
                email: string,
                phoneNumber: string
             }

             */
            return this.http.get(url).map(res => res.json());
    }

    getPass(url){

        /**
         {
            password: string;
         }

         */
        return this.http.get(url).map(res => res.json());
    }

    setUserBasicInfo(editUserInfo: Object, url){
        return this.http.post(
            url,
            JSON.stringify(editUserInfo)
        ).map(res => res.json());
    }

    setUserGeneralInfo(editGeneralData: Object, url){
        return this.http.post(
            url,
            JSON.stringify(editGeneralData)
        ).map(res => res.json());
    }

    setUserPassword(editUserPass, url){
        return this.http.post(
            url,
            JSON.stringify(editUserPass)
        ).map(res => res.json());
    }
}