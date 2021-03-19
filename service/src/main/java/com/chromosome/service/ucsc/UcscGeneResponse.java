package com.chromosome.service.ucsc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
class UcscGeneResponse {

    private String genome;
    private String chrom;
    private List<UcscGene> sgdGene;

    public UcscGeneResponse() {
    }

    public String getGenome() {
        return genome;
    }

    public void setGenome(String genome) {
        this.genome = genome;
    }

    public String getChrom() {
        return chrom;
    }

    public void setChrom(String chrom) {
        this.chrom = chrom;
    }

    public List<UcscGene> getSgdGene() {
        return sgdGene;
    }

    public void setSgdGene(List<UcscGene> sgdGene) {
        this.sgdGene = sgdGene;
    }
}
