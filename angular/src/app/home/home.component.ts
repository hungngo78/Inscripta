import { Component, OnInit } from '@angular/core';
import { GenomeService } from '../../genome.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private readonly genomeService: GenomeService) { }

  ngOnInit(): void {   

    this.fetchChromosomes();
  }


  // Illustrates interaction with backend API via angular service class. 
  private fetchChromosomes() {
    this.genomeService.listChromosomes().subscribe(results => {
      console.log("Chromosomes found: ");
      results.forEach((c: any) => {
        console.log(c.name);
      });
    });
  }
  
}
