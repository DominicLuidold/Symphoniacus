package at.fhv.teamb.symphoniacus.persistence.dao;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
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
    private TypedQuery<DutyEntity> mockedQuery;

    /**
     * Init method for all subsequent tests.
     */
    @BeforeAll
    void initialize() {
        this.entityManager = mock(EntityManager.class);
        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        when(entityManagerFactory.createEntityManager()).thenReturn(this.entityManager);

        List<DutyEntity> tempList = new LinkedList<>();
        tempList.add(new DutyEntity());
        tempList.add(new DutyEntity());
        // " select * from ... "
        when(this.entityManager.createQuery(anyString(), eq(DutyEntity.class)))
            .thenReturn(this.mockedQuery);
        when(this.mockedQuery.getResultList()).thenReturn(tempList);
        this.dao = new DutyDao();
    }
}
