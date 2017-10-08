import {Injectable} from '@angular/core';
import  'rxjs/add/operator/map';
import {HttpClient} from "../common/services/http-client.service";

@Injectable()
export class HomeService{

    constructor(private http: HttpClient){
        console.log('PostService Initialized...');

    }
}