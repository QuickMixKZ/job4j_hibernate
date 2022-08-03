import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Candidate;

import javax.persistence.Query;
import java.util.List;

public class HibernateRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Candidate candidate = create(new Candidate("Mikhail", 2, 100_000), sf);
            Candidate candidate1 = create(new Candidate("Alexandr", 1, 20_000), sf);
            Candidate candidate2 = create(new Candidate("Lev", 5, 200_000), sf);
            System.out.println(findAll(sf));
            System.out.println(findById(sf, candidate2.getId()));
            candidate2.setSalary(1000000000);
            update(sf, candidate2);
            System.out.println(findByName(sf, candidate2.getName()));
            System.out.println(delete(sf, candidate));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Candidate create(Candidate candidate, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(candidate);
        session.getTransaction().commit();
        session.close();
        return candidate;
    }

    public static List<Candidate> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.model.Candidate").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static Candidate findById(SessionFactory sf, int id) {
        Session session = sf.openSession();
        Query query = session.createQuery("from Candidate c where c.id = :fId");
        query.setParameter("fId", id);
        Candidate result = (Candidate) query.getSingleResult();
        session.close();
        return result;
    }

    public static List findByName(SessionFactory sf, String name) {
        Session session = sf.openSession();
        Query query = session.createQuery("from Candidate c where c.name = :fName");
        query.setParameter("fName", name);
        List result =  query.getResultList();
        session.close();
        return result;
    }

    public static void update(SessionFactory sf, Candidate candidate) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(
                "update Candidate c "
                        + "set c.name = :newName, "
                        + "c.experience = :newExperience, "
                        + "c.salary = :newSalary "
                        + "where c.id = :fId");
        query.setParameter("newName", candidate.getName());
        query.setParameter("newExperience", candidate.getExperience());
        query.setParameter("newSalary", candidate.getSalary());
        query.setParameter("fId", candidate.getId());
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public static boolean delete(SessionFactory sf, Candidate candidate) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete FROM Candidate where id = :fId");
        query.setParameter("fId", candidate.getId());
        boolean result = query.executeUpdate() > 0;
        transaction.commit();
        session.close();
        return result;
    }

}
