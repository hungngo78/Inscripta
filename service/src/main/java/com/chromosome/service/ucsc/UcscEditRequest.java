package com.chromosome.service.ucsc;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UcscEditRequest {
	private int id;
	private String chromosomeName;
	private String geneName;
    private Long cdsStart;
    private Long cdsEnd;
    private Long editPosition;
    private String nucleotide;
    private String dateTime;

    public UcscEditRequest() {
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public String getChromosomeName() {
        return chromosomeName;
    }

    public void setChromosomeName(String name) {
        this.chromosomeName = name;
    }
    
    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String name) {
        this.geneName = name;
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
    
    public Long getEditPosition() {
        return editPosition;
    }

    public void setEditPosition(Long position) {
        this.editPosition = position;
    }
    
    public String getNucleotide() {
        return nucleotide;
    }

    public void setNucleotide(String nucleotide) {
        this.nucleotide = nucleotide;
    }
    
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
