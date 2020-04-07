package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DutyDAOTest {
	private DutyDAO _dao;

	@BeforeAll
	public void init() {
		EntityManagerFactory fact = Persistence.createEntityManagerFactory("mysqldb");
		_dao = new DutyDAO();
	}

	@Test
	public void testFindAllDutiesForWeek() {
		List<Duty> list = _dao.findAllInRange(LocalDateTime.of(2020, 3,30,0,0,0), LocalDateTime.of(2020,4,5,0,0,0));

		Assertions.assertTrue(list != null);
	}
}
