package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import at.fhv.teamb.symphoniacus.persistence.dao.DutyDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DutyDAOMockedTests {

	private DutyDAO dao;
	private EntityManager entityManager;

	@BeforeAll
	public void init() {
		entityManager = mock(EntityManager.class);
		EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
		Mockito.when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);

		TypedQuery<Duty> mockedQuery = Mockito.mock(TypedQuery.class);

		List<Duty> tempList = new LinkedList<>();
		tempList.add(new Duty());
		tempList.add(new Duty());
		// " select * from ... "
		Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Duty.class))).thenReturn(mockedQuery);
		Mockito.when(mockedQuery.getResultList()).thenReturn(tempList);

		dao = new DutyDAO(entityManagerFactory);
	}

	@Test
	public void testFindAllDuties() {
		List<Duty> list = dao.findAll();
	}
}
