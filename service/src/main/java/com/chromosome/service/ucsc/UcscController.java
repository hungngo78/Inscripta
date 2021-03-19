package com.chromosome.service.ucsc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
//@RequestMapping(path = "/ucsc")
public class UcscController {
private static final Logger LOGGER = LoggerFactory.getLogger(UcscController.class);
	
	@Autowired
    private UcscSacCer3ChromosomeRepository chromosomeService;
	
	@Autowired
    private UcscSacCer3GeneRepository geneService;
	
	@Autowired
    private UcscSacCer3EditRequestRepository editRequestService;
	
	
	//location: http://localhost:8080/get_all_chromosomes
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path="/get_all_chromosomes", produces = "application/json")
	public ResponseEntity<Object> getAllChromosomes()
	{
		try {
			List<UcscChromosome> chromosomes = chromosomeService.getChromosomes();
			if (chromosomes != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				String chromosomesJson = objectMapper.writeValueAsString(chromosomes);
				
				return new ResponseEntity<Object>(chromosomesJson, HttpStatus.OK);
			}
		} catch (JsonProcessingException ex) {
			LOGGER.error(ex.getMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage());
		}
		
		return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
	
	//location: http://localhost:8080/get_all_editRequests
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path="/get_all_editRequests", produces = "application/json")
	public ResponseEntity<Object> getAllEditRequests()
	{
		try {
			List<UcscEditRequest> editRequests = editRequestService.getEditRequests();
			ObjectMapper objectMapper = new ObjectMapper();
			String chromosomesJson = objectMapper.writeValueAsString(editRequests);
			
			return new ResponseEntity<Object>(chromosomesJson, HttpStatus.OK);
		} catch (JsonProcessingException ex) {
			LOGGER.error(ex.getMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage());
		}
		
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	//location: http://localhost:8080/get_genes_by_chromosome/{chromosome}
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path="/get_genes_by_chromosome/{chromosome}", produces = "application/json")
	public ResponseEntity<Object> getGenesByChromosome(@PathVariable String chromosome)
	{
		try {
			List<UcscGene> genes = geneService.getGenes(chromosome);
			if (genes != null && genes.size() > 0) {
				ObjectMapper objectMapper = new ObjectMapper();
				String genesJson = objectMapper.writeValueAsString(genes);
				
				return new ResponseEntity<Object>(genesJson, HttpStatus.OK);
			}
		} catch (JsonProcessingException ex) {
			LOGGER.error(ex.getMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage());
		}
		
		return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
	
	//location: http://localhost:8080/add_editRequest
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path= "/add_editRequest", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> add_editRequest(@RequestBody UcscEditRequest editRequest) {
		try {
			List<UcscEditRequest> editRequests = editRequestService.addEditRequest(editRequest);
			return new ResponseEntity<Object>(editRequests, HttpStatus.OK);
		} catch (RuntimeException e) {
			LOGGER.error(e.getMessage());
		}
		
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
