package com.chromosome.service.ucsc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UcscGene {

    private String name;
    private Long cdsStart;
    private Long cdsEnd;

    public UcscGene() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //@JsonProperty("start")
    public Long getCdsStart() {
        return cdsStart;
    }

    //@JsonProperty("cdsStart")
    public void setCdsStart(Long cdsStart) {
        this.cdsStart = cdsStart;
    }

    //@JsonProperty("end")
    public Long getCdsEnd() {
        return cdsEnd;
    }

    //@JsonProperty("cdsEnd")
    public void setCdsEnd(Long cdsEnd) {
        this.cdsEnd = cdsEnd;
    }
}
