package com.chromosome.service.ucsc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class UcscSacCer3GeneRepositoryImpl implements UcscSacCer3GeneRepository {

    public static final String BASE_GENOME_URL = "https://api.genome.ucsc.edu/getData/track?genome=sacCer3;track=sgdGene;chrom=";
    private final ObjectMapper mapper;

    @Autowired
    public UcscSacCer3GeneRepositoryImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<UcscGene> getGenes(String chromosome) {

        try {
            String response = initiateHttpClientQuery(chromosome);
            UcscGeneResponse ucscGeneResponse = mapper.readValue(response, UcscGeneResponse.class);
            return ucscGeneResponse.getSgdGene();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String initiateHttpClientQuery(String chromosome) {

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(BASE_GENOME_URL + chromosome);
            HttpResponse response = client.execute(request);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
