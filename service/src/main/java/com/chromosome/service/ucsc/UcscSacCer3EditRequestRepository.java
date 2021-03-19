package com.chromosome.service.ucsc;

import java.util.List;

public interface UcscSacCer3EditRequestRepository {
	public List<UcscEditRequest> getEditRequests();
	List<UcscEditRequest> addEditRequest(UcscEditRequest newRequest);
}
