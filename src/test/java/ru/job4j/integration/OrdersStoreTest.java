package ru.job4j.integration;

import static org.junit.Assert.*;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void cleanUp() throws SQLException {
        pool.getConnection().prepareStatement("DROP TABLE IF EXISTS orders").executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderThenUpdateAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("name1", "description1");
        store.save(order);
        order.setDescription("new description");
        store.update(order);

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("new description"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveTwoOrdersThenFindById() {
        OrdersStore store = new OrdersStore(pool);
        Order order1 = store.save(Order.of("name1", "description1"));
        Order order2 = store.save(Order.of("name2", "description2"));

        Order orderInDb = store.findById(order1.getId());

        assertEquals(order1.getName(), orderInDb.getName());
        assertEquals(order1.getDescription(), orderInDb.getDescription());
    }

    @Test
    public void whenSaveTwoOrdersThenFindByName() {
        OrdersStore store = new OrdersStore(pool);
        Order order1 = store.save(Order.of("name1", "description1"));
        Order order2 = store.save(Order.of("name2", "description2"));

        List<Order> ordersByName = store.findByName(order1.getName());

        assertEquals(order1.getName(), ordersByName.get(0).getName());
        assertEquals(order1.getDescription(), ordersByName.get(0).getDescription());
    }
}