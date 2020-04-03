package life.anorak.census.explorer.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import life.anorak.census.explorer.entity.CensusLearn;
import life.anorak.census.explorer.repository.CensusLearnRepository;

@RestController
@RequestMapping("/rest/census/")
@CrossOrigin(origins = "http://localhost:4200")
public class CensusLearnController {

	protected static final Set<String> SORT_BY_COLUMNS = new HashSet<>(
			Arrays.asList(new String[] { "value", "age", "count" }));

	protected static final Set<String> SORT_DIRECTION = new HashSet<>(Arrays.asList(new String[] { "DESC", "ASC" }));

	@Autowired
	private CensusLearnRepository censusLearnRepository;

	@GetMapping("/learns")
	public Page<CensusLearn> getCensusLearn(//
			@RequestParam(defaultValue = "") String groupBy, //
			@RequestParam(defaultValue = "0") Integer pageNo, //
			@RequestParam(defaultValue = "100") Integer pageSize, //
			@RequestParam(defaultValue = "value") String sortBy, //
			@RequestParam(defaultValue = "ASC") String sortDirection) {

		groupBy = validGroupBy(groupBy);
		validPageSize(pageSize);
		sortBy = validSortBy(sortBy);
		Direction direction = validSortDirection(sortDirection);

		return getCensusLearnRepository().findGroupBy(groupBy,
				PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy)));
	}

	protected String validGroupBy(String groupBy) {
		List<String> variables = getVariables();
		if (StringUtils.isEmpty(groupBy)) {
			groupBy = variables.get(0);
		} else if (!variables.contains(groupBy)) {
			throw new IllegalArgumentException(new StringBuilder("Column groupBy")//
					.append(groupBy).append(" does not exist!")//
					.toString());
		}
		return groupBy;
	}

	protected void validPageSize(Integer pageSize) {
		if (pageSize > 100) {
			throw new IllegalArgumentException("Page size must be less than 100!");
		}
	}

	protected String validSortBy(String sortBy) {
		sortBy = sortBy.toLowerCase();
		if (!SORT_BY_COLUMNS.contains(sortBy)) {
			throw new IllegalArgumentException(new StringBuilder("Column sortBy")//
					.append(sortBy).append(" does not exist!")//
					.toString());
		}
		return sortBy;
	}

	protected Direction validSortDirection(String sortDirection) {
		sortDirection = sortDirection.toUpperCase();
		if (!SORT_DIRECTION.contains(sortDirection)) {
			throw new IllegalArgumentException("Invalid sort direction!");
		}
		return Direction.valueOf(sortDirection);
	}

	@GetMapping("/learns/variables")
	public List<String> getVariables() {
		return getCensusLearnRepository().findVariables();
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	protected CensusLearnRepository getCensusLearnRepository() {
		return censusLearnRepository;
	}

	protected void setCensusLearnRepository(CensusLearnRepository censusLearnRepository) {
		this.censusLearnRepository = censusLearnRepository;
	}

}
