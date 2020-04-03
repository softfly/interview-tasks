package life.anorak.census.explorer.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import life.anorak.census.explorer.entity.CensusLearn;

@Repository
public class CensusLearnRepositoryImpl implements CensusLearnRepository {

	protected static final String SQL_SELECT_CENSUS_LEARN =
			"SELECT `:groupby` as value, COUNT(*) as count, AVG(age) as age, ROWID as id "
			+ "FROM census_learn_sql "
			+ "WHERE value IS NOT NULL "
			+ "GROUP BY `:groupby` "
			+ "ORDER BY :orderby";

	protected static final String SQL_COUNT_CENSUS_LEARN =
			"SELECT DISTINCT COUNT(*) OVER () "
			+ "FROM census_learn_sql "
			+ "WHERE `:groupby` IS NOT NULL "
			+ "GROUP BY `:groupby`";

	protected static final String SQL_PRAGMA_CENSUS_LEARN = "PRAGMA table_info('census_learn_sql')";

	protected static final String GROUP_BY = ":groupby";

	protected static final String ORDER_BY = ":orderby";

	protected static final String FIELD_NAME = "name";

    @PersistenceContext
    EntityManager em;

    @Autowired
    Connection con;

    public Page<CensusLearn> findGroupBy(String groupBy, Pageable paging) {
		return new PageImpl<>(
				findGroupByQuery(groupBy, paging),
				paging,
				countGroupByQuery(groupBy));
    }

    @SuppressWarnings("unchecked")
	public List<CensusLearn> findGroupByQuery(String groupBy, Pageable paging) {
    	return em.createNativeQuery(
    			SQL_SELECT_CENSUS_LEARN
    			.replace(GROUP_BY, groupBy)
    			.replace(ORDER_BY, getSqlOrder(paging)), CensusLearn.class)
    			.setFirstResult(paging.getPageNumber() * paging.getPageSize())
    			.setMaxResults(paging.getPageSize())
    			.getResultList();
    }

    public int countGroupByQuery(String groupBy) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_COUNT_CENSUS_LEARN.replace(GROUP_BY, groupBy));
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
    }

    @Cacheable
    public List<String> findVariables() {
    	List<String> variables = new LinkedList<>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_PRAGMA_CENSUS_LEARN);
			while (rs.next()) {
				variables.add(rs.getString(FIELD_NAME));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.sort(variables, Comparator.comparing(String::toString));
		return variables.isEmpty() ? Collections.emptyList() : variables;
	}

    protected static String getSqlOrder(Pageable paging) {
    	return new StringBuilder(paging.getSort().get()//
    			.map(CensusLearnRepositoryImpl::getSqlOrder)//
    			.collect(Collectors.joining(",")))
    			.toString();
    }

    protected static String getSqlOrder(Order o) {
    	return new StringBuilder(o.getProperty())//
    			.append(" ")//
    			.append(o.getDirection().name())//
    			.toString();
    }
}
