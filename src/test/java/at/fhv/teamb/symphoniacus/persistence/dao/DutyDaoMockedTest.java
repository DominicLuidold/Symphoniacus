package at.fhv.teamb.symphoniacus.persistence.dao;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DutyDaoMockedTest {
    private DutyDao dao;
    private EntityManager entityManager;
    @Mock
    private TypedQuery<Duty> mockedQuery;

    /**
     * Init method for all subsequent tests.
     */
    @BeforeAll
    void initialize() {
        this.entityManager = mock(EntityManager.class);
        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        when(entityManagerFactory.createEntityManager()).thenReturn(this.entityManager);

        List<Duty> tempList = new LinkedList<>();
        tempList.add(new Duty());
        tempList.add(new Duty());
        // " select * from ... "
        when(this.entityManager.createQuery(anyString(), eq(Duty.class)))
            .thenReturn(this.mockedQuery);
        when(this.mockedQuery.getResultList()).thenReturn(tempList);
        this.dao = new DutyDao();
    }
}
