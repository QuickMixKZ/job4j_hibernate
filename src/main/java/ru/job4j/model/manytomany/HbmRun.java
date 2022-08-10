package ru.job4j.model.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Author jackLondon = new Author("Jack London");
            jackLondon.addBook(new Book("Burning Daylight"));
            jackLondon.addBook(new Book("John Barleycorn"));
            jackLondon.addBook(new Book("Jerry of the Islands"));
            Author markTwain = new Author("Mark Twain");
            markTwain.addBook(new Book("The Adventures of Tom Sawyer"));
            markTwain.addBook(new Book("The Adventures of Huckleberry Finn"));
            Author sergeyEsenin = new Author("Sergey Esenin");
            sergeyEsenin.addBook(new Book("Poems"));

            session.persist(jackLondon);
            session.persist(markTwain);
            session.persist(sergeyEsenin);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
