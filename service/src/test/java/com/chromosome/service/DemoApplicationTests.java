package com.chromosome.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.chromosome.service.ucsc.UcscChromosome;
import com.chromosome.service.ucsc.UcscController;
import com.chromosome.service.ucsc.UcscEditRequest;
import com.chromosome.service.ucsc.UcscSacCer3ChromosomeRepository;
import com.chromosome.service.ucsc.UcscSacCer3EditRequestRepository;
import com.chromosome.service.ucsc.UcscSacCer3GeneRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@SpringBootApplication //(classes=DemoApplicationTests.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
//@WebMvcTest(UcscController.class)
//@AutoConfigureMockMvc

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class DemoApplicationTests  {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UcscSacCer3ChromosomeRepository empService;

	@MockBean
    private UcscSacCer3GeneRepository geneService;
	
	@MockBean
    private UcscSacCer3EditRequestRepository editRequestService;

	private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    private <T> T mapFromJson(String json, Class<T> clazz)
        throws JsonParseException, JsonMappingException, IOException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }
    
	@Test
	void TestGetAllChromosomes1() throws Exception {
		String uri = "/get_all_chromosomes";
		
		// prepare data and mock's behaviour
		
		// execute
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    
	    String content = mvcResult.getResponse().getContentAsString();
	    UcscChromosome[] chromosomeslist = mapFromJson(content, UcscChromosome[].class);
	    assertTrue(chromosomeslist.length > 0);
	}
	
	@Test
	void TestGetAllChromosomes2() throws Exception {
		String uri = "/get_all_editRequests";
		
		// prepare data and mock's behaviour
		UcscChromosome chromosomeStub1 = new UcscChromosome();
		chromosomeStub1.setName("AA");
		chromosomeStub1.setLength(2L);
		
		UcscChromosome chromosomeStub2 = new UcscChromosome();
		chromosomeStub1.setName("BB");
		chromosomeStub1.setLength(4L);
		
		List<UcscChromosome> dummyList = new ArrayList();
		dummyList.add(chromosomeStub1);
		dummyList.add(chromosomeStub2);
		Mockito.when(empService.getChromosomes()).thenReturn(dummyList);
		
		// execute
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
	
	    int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
	    
	    String content = mvcResult.getResponse().getContentAsString();
	    UcscChromosome[] chromosomeslist = mapFromJson(content, UcscChromosome[].class);
	    assertTrue(chromosomeslist.length > 0);
	    
	    assertEquals(chromosomeslist[0].getName(), "AA");
	    assertEquals(chromosomeslist[1].getName(), "BB");
	    assertEquals(chromosomeslist[0].getLength(), new Long(2L));
	    assertEquals(chromosomeslist[1].getLength(), new Long(4L));
	}

	@Test 
	void TestGetAllPreviousEditRequest() throws Exception {
	    assertEquals(1, 1);
	}
	
	@Test
	void TestAddNewEditRequest1() throws Exception {
		// prepare data and mock's behaviour
		UcscEditRequest stub = new UcscEditRequest();
		stub.setChromosomeName("chrIX");
		stub.setGeneName("YDL246C");
		stub.setCdsStart(1234L);
		stub.setCdsStart(2345L);
		stub.setEditPosition(1500L);
		stub.setNucleotide("ABCD");
		
		List<UcscEditRequest> dummyList = new ArrayList();
		dummyList.add(stub);
		Mockito.when(editRequestService.addEditRequest(stub)).thenReturn(dummyList);
		
		String uri = "/add_editRequest";
		// execute
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(stub))).andReturn();
		
		// verify
		int status = mvcResult.getResponse().getStatus();
	    assertEquals(200, status);
		
	    String content = mvcResult.getResponse().getContentAsString();
	    UcscEditRequest[] requestlist = mapFromJson(content, UcscEditRequest[].class);
	    assertTrue(requestlist.length > 0);
	    
	    assertEquals(requestlist[0].getId(), 1);
	    assertEquals(requestlist[0].getChromosomeName(), "chrIX");
	    assertEquals(requestlist[0].getGeneName(), "YDL246C");
	    assertEquals(requestlist[0].getCdsStart(), new Long(1234L));
	    assertEquals(requestlist[0].getCdsEnd(), new Long(2345L));
	    assertEquals(requestlist[0].getEditPosition(), new Long(1500L));
	    assertEquals(requestlist[0].getNucleotide(), "ABCD");
	}
}
