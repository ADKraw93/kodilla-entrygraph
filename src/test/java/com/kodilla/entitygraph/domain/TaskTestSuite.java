package com.kodilla.entitygraph.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskTestSuite {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private List<Long> insertExampleData() {
        Person p1 = new Person(null, "John", "Smith");
        Person p2 = new Person(null, "Johnny", "Bravo");
        Subtask s1 = new Subtask(null, "Prepare food", "done", List.of(p1));
        Subtask s2 = new Subtask(null, "Clean house", "in progress", List.of(p2));
        Subtask s3 = new Subtask(null, "Decorate house", "to do", List.of(p1, p2));
        Subtask s4 = new Subtask(null, "Do maths", "to do", List.of(p2));
        Task t1 = new Task(null, "Prepare a party", "in progress", List.of(p1, p2), List.of(s1, s2, s3));
        Task t2 = new Task(null, "Do homework", "Done", List.of(p2), List.of(s4));

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.persist(s1);
        em.persist(s2);
        em.persist(s3);
        em.persist(s4);
        em.persist(t1);
        em.persist(t2);
        em.flush();
        em.getTransaction().commit();
        em.close();

        return List.of(t1.getId(), t2.getId());
    }

    @Test
    void shouldNPlusOneProblemOccure() {
        //Given
        List<Long> savedTasks = insertExampleData();
        EntityManager em = emf.createEntityManager();

        //When
        System.out.println("****************** BEGIN OF FETCHING *******************");
        System.out.println("*** STEP 1 – query for tasks ***");

        List<Task> tasks =
                em.createQuery(
                        "from Task "
                                + " where id in (" + tasksIds(savedTasks) + ")",
                        Task.class).getResultList();

        /*TypedQuery<Invoice> query = em.createQuery(
                "from Invoice "
                        + " where id in (" + invoiceIds(savedInvoices) + ")",
                Invoice.class);

        EntityGraph<Invoice> eg = em.createEntityGraph(Invoice.class);
        eg.addAttributeNodes("customer");
        eg.addSubgraph("items").addAttributeNodes("product");
        query.setHint("javax.persistence.fetchgraph", eg);*/

        //List<Task> tasks = query.getResultList();

        for (Task task : tasks) {
            System.out.println("*** STEP 2 – read data from task ***");
            System.out.println(task);
            System.out.println("*** STEP 3 – read the persons data ***");
            System.out.println(task.getPersons());

            for (Subtask subtask : task.getSubtasks()) {
                System.out.println("*** STEP 4 – read the subtask ***");
                System.out.println(subtask);
                System.out.println("*** STEP 5 – read the persons from subtask ***");
                System.out.println(subtask.getPersons());
            }

        }

        System.out.println("****************** END OF FETCHING *******************");

        //Then
        //Here should be some assertions and the clean up performed

    }

    private String tasksIds(List<Long> tasksIds) {
        return tasksIds.stream()
                .map(n -> "" + n)
                .collect(Collectors.joining(","));
    }
}