package life.anorak.census.explorer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import life.anorak.census.explorer.entity.CensusLearn;

public interface CensusLearnRepository {

	Page<CensusLearn> findGroupBy(String groupBy, Pageable paging);

	List<String> findVariables();

}
