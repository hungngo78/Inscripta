package com.chromosome.service.ucsc;

import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UcscSacCer3EditRequestRepositoryImpl implements UcscSacCer3EditRequestRepository {

	@Override
    public List<UcscEditRequest> getEditRequests() {
		try {
		    // create object mapper instance
		    ObjectMapper mapper = new ObjectMapper();
		    
		    // check file empty
		    List<UcscEditRequest> editRequests = null;
		    File file1 = Paths.get("editRequest.json").toFile();
		    if  (file1.length() != 0) {		    
		    	List<UcscEditRequest> tmpList = Arrays.asList(mapper.readValue(file1, UcscEditRequest[].class));
		    	editRequests = new ArrayList<UcscEditRequest>(tmpList);
		    } else 
		    	editRequests = new ArrayList<UcscEditRequest>();
		    		    
		    return editRequests;
		} catch (Exception ex) {
		    ex.printStackTrace();
		    throw new RuntimeException(ex.getMessage(), ex);
		}
    }
	
	@Override
	public List<UcscEditRequest> addEditRequest(UcscEditRequest newRequest) {
		int maxId = 0;
		
		try {
		    // create object mapper instance
		    ObjectMapper mapper = new ObjectMapper();
		    
		    LocalDateTime now = LocalDateTime.now();
		    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		    String formatDateTime = now.format(format); 		   
		    
		    // check file empty
		    List<UcscEditRequest> editRequests = null;
		    File file1 = Paths.get("editRequest.json").toFile();
		    if  (file1.length() != 0) {		    
		    	List<UcscEditRequest> tmpList = Arrays.asList(mapper.readValue(file1, UcscEditRequest[].class));
		    	editRequests = new ArrayList<UcscEditRequest>(tmpList);
		    	maxId = editRequests.size();
		    } 
		    else { 
		    	editRequests = new ArrayList<UcscEditRequest>();
		    }
		    newRequest.setId(++maxId);
		    newRequest.setDateTime(formatDateTime);
		    editRequests.add(newRequest);
		    
		    // convert objects to JSON file
		    mapper.writeValue(Paths.get("editRequest.json").toFile(), editRequests);
		    
		    return editRequests;
		} catch (Exception ex) {
		    ex.printStackTrace();
		    throw new RuntimeException(ex.getMessage(), ex);
		}
	}

}
