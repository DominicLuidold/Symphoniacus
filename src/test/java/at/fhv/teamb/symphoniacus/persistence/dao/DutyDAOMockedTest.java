package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DutyDAOMockedTest {

	private DutyDAO dao;
	private EntityManager entityManager;
	@Mock
	private TypedQuery<Duty> mockedQuery;

	@BeforeAll
	public void init() {
		this.entityManager = mock(EntityManager.class);
		EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
		Mockito.when(entityManagerFactory.createEntityManager()).thenReturn(this.entityManager);

		List<Duty> tempList = new LinkedList<>();
		tempList.add(new Duty());
		tempList.add(new Duty());
		// " select * from ... "
		Mockito.when(this.entityManager.createQuery(Mockito.anyString(), Mockito.eq(Duty.class))).thenReturn(this.mockedQuery);
		Mockito.when(this.mockedQuery.getResultList()).thenReturn(tempList);
		this.dao = new DutyDAO();
	}

}
