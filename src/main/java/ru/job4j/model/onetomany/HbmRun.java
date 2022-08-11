package ru.job4j.model.onetomany;

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
            CarBrand toyota = new CarBrand("Toyota");
            CarModel rav4 = new CarModel("RAV4");
            rav4.setBrand(toyota);
            CarModel camry = new CarModel("RAV4");
            camry.setBrand(toyota);
            CarModel corolla = new CarModel("RAV4");
            corolla.setBrand(toyota);
            CarModel fjCruiser = new CarModel("RAV4");
            fjCruiser.setBrand(toyota);
            CarModel landCruiser = new CarModel("RAV4");
            landCruiser.setBrand(toyota);
            toyota.addModel(rav4);
            toyota.addModel(camry);
            toyota.addModel(corolla);
            toyota.addModel(fjCruiser);
            toyota.addModel(landCruiser);
            session.save(toyota);
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
