import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import { Horse } from '../dto/horse';

const baseUri = environment.backendUrl + '/horses';

@Injectable({
  providedIn: 'root'
})
export class HorseService {
  
  constructor(private httpClient: HttpClient) {
  }

  /**
   * Loads specific sport from the backend
   *
   * @param id of sport to load
   */
  getHorseById(id: number): Observable<Horse> {
    console.log('Load Horse details for ' + id);
    return this.httpClient.get<Horse>(baseUri + '/' + id);
  }

  /**
   * Fetches all sports from the backend.
   */
  getAllHorses(): Observable<Horse[]> {
    console.log('Load all horses');
    return this.httpClient.get<Horse[]>(baseUri + '/all');
  }

  getAllChildren(): Observable<Horse[]> {
    console.log('Load all horse children');
    return this.httpClient.get<Horse[]>(baseUri + '/children');
  }

  createHorse(horse: Horse): Observable<Horse> {
    console.log('Create new sport', horse);
    return this.httpClient.post<Horse>(
      baseUri + '/add',
      horse
    );
  }
 
  updateHorse(horse: Horse): Observable<Horse> {
    
    console.log('updateHorse', horse);
    return this.httpClient.put<Horse>(
      baseUri + '/update',
      horse
    );
  }

  deleteHorseById(id: number): Observable<void> {
    console.log('Delete Horse with id ' + id);
    return this.httpClient.delete<void>(baseUri + '/delete/' + id);
  }
  
  searchHorse(horse: Horse): Observable<Horse[]> {
    console.log('Create new sport', horse);
    return this.httpClient.post<Horse[]>(
      baseUri + '/find',
      horse
    );
  }


}
