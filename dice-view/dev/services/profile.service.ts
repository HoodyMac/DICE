import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "../common/services/http-client.service";

@Injectable()
export class ProfileService{

    constructor(private http: HttpClient){
        console.log('PostService Initialized...');

    }

    getModule(url){
        /**
             {
                label:string; ('Friends' , 'Messages', 'Groups', 'Music', 'Photos')
                value:number; (55, 55, 86, 42, 74);
             }
         */
        return this.http.get(url).map(res => res.json());
    }

    getUserInfo(url){
        /**
             {
                originalImgSrc: string;
                cropImgSrc: string;
                username:string;
                city:string;
                education:string;
                work:string;
                age:string;
                prgLanguages:string;
                phoneNumber:string;
                isOnline:boolean;
            }
         */
        return this.http.get(url).map(res => res.json());
    }

    postCoordsImageCrop(cropImgData: Object, url){
            return this.http.post(
                url,
                JSON.stringify(cropImgData)
            ).map(res => res.json());
    }

}