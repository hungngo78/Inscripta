package com.chromosome.service.ucsc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UcscSacCer3ChromosomeRepositoryImpl implements UcscSacCer3ChromosomeRepository {

    private static final String BASE_GENOME_URL = "https://api.genome.ucsc.edu/list/chromosomes?genome=sacCer3";
    private final ObjectMapper mapper;

    @Autowired
    public UcscSacCer3ChromosomeRepositoryImpl(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @Override
    public List<UcscChromosome> getChromosomes() {
        String jsonValue = initiateHttpClientQuery();
        try {
            JsonNode jsonNode = mapper.readTree(jsonValue);
            JsonNode chromosomes = jsonNode.get("chromosomes");
            List<UcscChromosome> chrList = new ArrayList<>();
            chromosomes.fieldNames()
                    .forEachRemaining((value)->
                            chrList.add(
                                    new UcscChromosome(value, chromosomes.get(value).asLong())
                            )
                    );
            return chrList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String initiateHttpClientQuery() {

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(new HttpGet(BASE_GENOME_URL));
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
