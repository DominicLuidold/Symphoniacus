package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DutyDAOMockedTests {

	private DutyDAO _dao;
	private EntityManager _entityManager;

	@BeforeAll
	public void init() {
		_entityManager = mock(EntityManager.class);
		EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
		Mockito.when(entityManagerFactory.createEntityManager()).thenReturn(_entityManager);

		TypedQuery<Duty> mockedQuery = Mockito.mock(TypedQuery.class);

		List<Duty> tempList = new LinkedList<>();
		tempList.add(new Duty());
		tempList.add(new Duty());
		// " select * from ... "
		Mockito.when(_entityManager.createQuery(Mockito.anyString(), Mockito.eq(Duty.class))).thenReturn(mockedQuery);
		Mockito.when(mockedQuery.getResultList()).thenReturn(tempList);

		_dao = new DutyDAO(entityManagerFactory);
	}

	@Test
	public void testFindAllDuties() {
		List<Duty> list = _dao.findAll();
	}
}
