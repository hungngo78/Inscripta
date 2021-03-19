import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GenomeService {

  constructor(private readonly http: HttpClient,) { }

  listGenes(chromosome: string): Observable<any> {
    return this.http.get<any[]>("/server/ucsc/sacCer3/genes",
        {
          params: new HttpParams().set("chromosome", chromosome)
        }
    );
  }

  listChromosomes(): Observable<any> {
    return this.http.get<any[]>("/server/ucsc/sacCer3/chromosomes",{});
  }
}
