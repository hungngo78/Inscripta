package com.chromosome.service.ucsc;

import java.util.List;

public interface UcscSacCer3GeneRepository {
    List<UcscGene> getGenes(String chromosome);
}
