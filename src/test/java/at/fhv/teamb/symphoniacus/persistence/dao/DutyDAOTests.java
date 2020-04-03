package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DutyDAOTests {

	private DutyDAO dao;

	@BeforeAll
	public void init() {
		EntityManagerFactory fact = Persistence.createEntityManagerFactory("mysqldb");
		dao = new DutyDAO(fact);
	}

	@Test
	public void testFindAllDuties() {
		List<Duty> list = dao.findAll();
		Assertions.assertTrue(list != null);
	}
}
