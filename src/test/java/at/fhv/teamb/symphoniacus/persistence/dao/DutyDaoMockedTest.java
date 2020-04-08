package at.fhv.teamb.symphoniacus.persistence.dao;

import static org.mockito.Mockito.mock;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DutyDaoMockedTest {
    private DutyDao dao;
    private EntityManager entityManager;
    @Mock
    private TypedQuery<Duty> mockedQuery;

    /**
     * Init method for all subsequent tests.
     */
    @BeforeAll
    public void init() {
        this.entityManager = mock(EntityManager.class);
        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        Mockito.when(entityManagerFactory.createEntityManager()).thenReturn(this.entityManager);

        List<Duty> tempList = new LinkedList<>();
        tempList.add(new Duty());
        tempList.add(new Duty());
        // " select * from ... "
        Mockito.when(this.entityManager.createQuery(Mockito.anyString(), Mockito.eq(Duty.class)))
            .thenReturn(this.mockedQuery);
        Mockito.when(this.mockedQuery.getResultList()).thenReturn(tempList);
        this.dao = new DutyDao();
    }
}
