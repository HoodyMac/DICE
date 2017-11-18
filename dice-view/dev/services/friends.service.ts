import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "../common/services/http-client.service";

@Injectable()
export class FriendsService {

    constructor(private http: HttpClient) {
        console.log('PostService Initialized...');
    }

    getUserFriendsData(){
        return this.http.get("/api/friends").map(res => res.json());
    }

    getUserNewFriendsData(){
        return this.http.get("/api/friendRequests").map(res => res.json());
    }

    acceptFriendsRequest(idUser){
        return this.http
            .put("/api/acceptFriendRequest/" + idUser);
    }

    rejectFriendRequest(idUser){
        return this.http
            .put("/api/rejectFriendRequest/" + idUser);
    }

    removeFriend(idUser){
        return this.http
            .delete("/api/removeFriend/" + idUser);
    }
}
