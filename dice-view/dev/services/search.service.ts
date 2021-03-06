import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "../common/services/http-client.service";

@Injectable()
export class SearchService {

    constructor(private http: HttpClient) {}

    getUserSearchData(userSearchData){
        return this.http.post("/api/search", userSearchData).map(res => {
            return res.json()
        });
    }

    addToFriends(idUser){
        return this.http.post("/api/friendRequest/" + idUser, null);
    }
}
