package life.anorak.census.explorer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name="census_learn_sql")
@IdClass(CensusLearn.class)
@Immutable
public class CensusLearn implements Serializable {

	@Id
	private Integer id;

	@Column
	private String value;

    @Column
	private Integer count;

    @Column
	private Float age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Float getAge() {
		return age;
	}

	public void setAge(Float age) {
		this.age = age;
	}

}
