import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class WebClientService {
  private baseUrl: string = 'http://localhost:8080';
  private editRequestsUrl: string = "/get_all_editRequests";
  private chromosomesUrl: string = "/get_all_chromosomes";
  private genesUrl: string = "/get_genes_by_chromosome";
  private addEditRequestUrl: string = "/add_editRequest";

  constructor(private http: HttpClient) { }

  private getHeaders() {
    const headers = new HttpHeaders();
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/json');
    headers.append('Access-Control-Allow-Origin', '*');
    return headers;
  }

  getAllEditRequests(): Observable<any> {
    return this.http
       .get<EditRequest[]>(`${this.baseUrl}${this.editRequestsUrl}`);
  }

  getChromosomes(): Observable<any> {
    return this.http
       .get<Chromosome[]>(`${this.baseUrl}${this.chromosomesUrl}`);
  }

  getGenesByChromosome(chromosome:string): Observable<any> {
    return this.http
      .get<Gene[]>(`${this.baseUrl}${this.genesUrl}/${chromosome}`);
  }

  postNewEditRequest(form: string): Observable<any> {
    const headers = { 'content-type': 'application/json'}  

    return this.http
      .post<EditRequest[]>(`${this.baseUrl}${this.addEditRequestUrl}`, form,  {headers: headers});
  }

  
}

export interface Chromosome {
  name: string;
  length: number;
}

export interface Gene {
  name: string;
  cdsStart: number;
  cdsEnd: number;
}

export class EditRequest {
  constructor(
  public id: number,
  public chromosomeName: string,
  public geneName: string,
  public cdsStart: number,
  public cdsEnd: number,
  public editPosition: number,
  public nucleotide: string,
  public dateTime: string) {}
}
