package org.ateam.ateam.domain.linko.service;

import java.util.List;
import org.ateam.ateam.domain.linko.controller.response.LinkoCardResponse;

public interface LinkoQueryService {
	List<LinkoCardResponse> getCards();
}
