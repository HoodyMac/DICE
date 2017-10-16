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
        return this.http.get("/api/me").map(res => res.json());
    }

    setUserBasicInfo(editUserInfo: Object){
        return this.http.put("/api/profile/", editUserInfo).map(res => {
            return res.json();
        });
    }

    setUserEmail(editGeneralData: any){
        return this.http.put("/api/account", editGeneralData).map(res => res.json());
    }

    setUserPassword(editUserPass){
        console.log(editUserPass);
        return this.http.put(
            "/api/account", editUserPass
        ).map(res => res.json());
    }
}