import { Component, OnInit } from '@angular/core';
import { WebClientService, Chromosome, Gene, EditRequest } from '../web-client.service'
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-edit-request',
  templateUrl: './edit-request.component.html',
  styleUrls: ['./edit-request.component.scss']
})
export class EditRequestComponent implements OnInit {
  editRequestForm: FormGroup;

  // temporary data (chromosome, gene, editRequest) gotten from RestAPI
  chromosomes: Chromosome[] = [];
  genes: Gene[] = [];
  editRequests: EditRequest[] = []; 

  // displayed columns of previous editRequest table
  displayedColumns: string[] = ['id', 'chromosome', 'geneName', 'geneStart', 'geneEnd', 'editPosition', 'nucleotide', 'dateTime'];

  constructor(private webClient: WebClientService, private formBuilder: FormBuilder) {
    this.editRequestForm = this.formBuilder.group({
      chromosome: ['', [Validators.required]],
      gene: ['', [Validators.required]],
      editPosition: [''],
      nucleotide: ['']
    });
  }

  ngOnInit(): void {
    console.log("----------------ngOnInit----");

    // get chromosome list from api
    this.webClient.getChromosomes().subscribe((data : Chromosome[]) => {
      this.chromosomes = data;
    }
    );

    // get all previous editRequest from api
    this.webClient.getAllEditRequests().subscribe((data : EditRequest[]) => {
      console.log(data)
      this.editRequests = data;
    }
    );
  }

  // handle Chromosome dropdown change event
  public onChromoChange(event: string) {
    // inquire gene list from api by selected chromosome name
    this.webClient.getGenesByChromosome(event).subscribe((data: Gene[]) => {
      this.genes = data;
    }
    );
  }

  // handle Gene dropdown change event
  private cdsStart: number = 0;
  private cdsEnd: number = 0;
  public onGeneChange(event: string) {
    for (let i = 0; i < this.genes.length; i++) {
      if (this.genes[i].name == event) {
        this.cdsStart = this.genes[i].cdsStart;
        this.cdsEnd = this.genes[i].cdsEnd;
        break;
      }
    }
  }

  // submit a new EditRequest onto Rest API
  public submit() {
    if (!this.editRequestForm.valid) {
      return;
    }
    const editRequest: EditRequest = new EditRequest(0, this.editRequestForm.get('chromosome')!.value, 
                                                    this.editRequestForm.get('gene')!.value,
                                                    this.cdsStart,
                                                    this.cdsEnd,
                                                    this.editRequestForm.get('editPosition')!.value,
                                                    this.editRequestForm.get('nucleotide')!.value,
                                                    '');
    const serializedForm = JSON.stringify(editRequest);

    this.webClient.postNewEditRequest(serializedForm).subscribe((data: EditRequest[]) => {
        console.log(data)
        this.editRequests = data;
      },
      (error) => console.log(error)
    );
  }
}


