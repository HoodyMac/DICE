import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "../common/services/http-client.service";

@Injectable()
export class EditService{

    constructor(private http: HttpClient){
        console.log('PostService Initialized...');

    }
    getUserBasicInfo(id){
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
        return this.http.get("/api/user/"+id).map(res => res.json());
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

    setUserBasicInfo(editUserInfo: Object, id){
        return this.http.put("/api/profile/"+id, editUserInfo).map(res => {
            return res.json();
        });
    }

    setUserGeneralInfo(editGeneralData: any, url){
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