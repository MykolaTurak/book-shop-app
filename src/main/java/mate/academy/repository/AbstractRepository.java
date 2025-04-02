package mate.academy.repository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AbstractRepository {
    protected final SessionFactory factory;

    @Autowired
    protected AbstractRepository(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }
}