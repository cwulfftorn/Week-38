 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Student;
import entity.Teacher;
import facade.Facade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        Facade facade = Facade.getFacadeInstance(emf);
       
        // opg 1
        System.out.println("Number of Students: " +facade.getAllStudents().size());
        
        // opg 2
        System.out.println("All student with first name Anders: " +facade.getStudentsByFirstName("Anders").size());
        
        // opg 3
        Student studentNew = facade.addStudent("Carl", "Johan");
        System.out.println("New Student with id : "+ studentNew.getId());
        
        // opg 4 (simple) id 1 = CLcos-v14e
        facade.assignStudentToSemester(studentNew.getId(),1l);
        
        // opg 7
        System.out.println("Number of Students: " +facade.studentCount());
        
        // opg 8
        System.out.println("Students on semester CLcos-v14e " +facade.studentCountForSemester("CLcos-v14e"));
        System.out.println("Students on semester CLcos-v14e " +facade.studentCountForSemester("CLdat-a14e"));
        System.out.println("Students on semester CLcos-v14e " +facade.studentCountForSemester("CLcos-v14e"));
        
        // opg 9
        System.out.println("Students assigned to all semesters: "+facade.studentCountAllSemesters());
        
        // opg 10
        List<Teacher> teachers = facade.teachesMostSemesters();
        System.out.println("Teaches most semesters (count) "+teachers.size());
        System.out.println("Teaches most semesters (name of first) "+teachers.get(0).getFirstname());
    }

}
