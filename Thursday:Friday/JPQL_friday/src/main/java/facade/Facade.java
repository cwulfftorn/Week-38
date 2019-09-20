/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Semester;
import entity.Student;
import entity.Teacher;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Facade {

    private static Facade instance;
    private static EntityManagerFactory emf;

    private Facade() {
    }

    public static Facade getFacadeInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Student> getAllStudents() {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Student.findAll").getResultList();
        } finally {
            em.close();
        }
    }

    public List<Student> getStudentsByFirstName(String fName) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery q = em.createNamedQuery("Student.findByFirstname", Student.class);
            q.setParameter("firstname", fName);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int studentCountForSemester(String semesterName) {
        EntityManager em = getEntityManager();
        try {
            String queryStr = "SELECT size(s.studentCollection) from Semester s where s.name = :semestername";
            Query query = em.createQuery(queryStr).setParameter("semestername", semesterName);
            int result = (int) query.getSingleResult();
            return result;

        } finally {
            em.close();
        }
    }

    public Student addStudent(String fName, String lName) {
        EntityManager em = getEntityManager();
        Student s = new Student(fName, lName);
        try {
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
            return s;
        } finally {
            em.close();
        }
    }

    public int studentCountAllSemesters() {
        EntityManager em = getEntityManager();
        try {
            String queryStr = "SELECT sum(size(s.studentCollection)) from Semester s";
            Query query = em.createQuery(queryStr);
            Long result = (Long) query.getSingleResult();
            return result.intValue();

        } finally {
            em.close();
        }
    }

    public List<Teacher> teachesMostSemesters() {
        EntityManager em = getEntityManager();
        try {
            String queryStr = "SELECT t from Teacher t where size(t.semesterCollection) =  (Select max(size(t.semesterCollection)) from Teacher t)";
            TypedQuery<Teacher> query = em.createQuery(queryStr, Teacher.class);
            List<Teacher> result = query.getResultList();
            return result;

        } finally {
            em.close();
        }
    }

    public Student assignStudentToSemester(long studentId, Long semesterId) {
        EntityManager em = getEntityManager();
        Student s = em.find(Student.class, studentId);
        Semester sem = em.find(Semester.class, semesterId);
        sem.addStudentToSemester(s);

        try {
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
            return s;
        } finally {
            em.close();
        }
    }

    public int studentCount() {
        EntityManager em = getEntityManager();
        try {
            Long count = (Long) em.createQuery("select COUNT(s) from Student s").getSingleResult();
            return count.intValue();
        } finally {
            em.close();
        }
    }

}
