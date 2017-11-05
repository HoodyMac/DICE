import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "../common/services/http-client.service";

@Injectable()
export class SearchService {

    constructor(private http: HttpClient) {
        console.log('PostService Initialized...');
    }

    getUserSearchData(userSearchData){
        return this.http.post("/api/search", userSearchData).map(res => {
            return res.json()
        });
    }
}