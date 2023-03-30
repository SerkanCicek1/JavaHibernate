package com.hb01.annotation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.List;

public class RunnerFetch01 {

    public static void main(String[] args) {

        Configuration con = new Configuration().
                configure("hibernate.cfg.xml").
                addAnnotatedClass(Student01.class);

        SessionFactory sf = con.buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        /*
        DB den bilgi almak istiyorsam
            1) get()
            2) SQL
            3) HQL
         */

        // 1. Yol : get()
        Student01 studendt1 = session.get(Student01.class, 1001);
        Student01 studendt2 = session.get(Student01.class, 1002);
        Student01 studendt3 = session.get(Student01.class, 1003);

        System.out.println(studendt1);
        System.out.println(studendt2);
        System.out.println(studendt3);

        // 2. Yol : SQL
        //String sqlQuery = "SELECT * FROM t_student01";
        //List<Object[]> resultList = session.createQuery(sqlQuery).getResultList();
//
        //for (Object[] object: resultList){
        //    System.out.println(Arrays.toString(object));
        //}

        // 3. Yol : HQL
        // Trick: HQL sorgusunda From dan sonra sinif ismin kullanilmali
        String hqlQuery = "FROM Student01";
        List<Student01> resultList2 = session.createQuery(hqlQuery, Student01.class).getResultList();

        for (Student01 student01: resultList2){
            System.out.println(student01);
        }

        // Donecek kaydin unique oldugundan eminsem uniqueResult()

        // SQL ile
        String sqlQuery2 = "SELECT * FROM t_student01 WHERE student_name ='Cemil Bey'";
        Object[] uniqueResult1 = (Object[]) session.createSQLQuery(sqlQuery2).uniqueResult();
        System.out.println(Arrays.toString(uniqueResult1));

        // yukarda 1 obje gelecek ama icinde fieldlar oldugu icin bunlari ayri almak istersem:
        System.out.println(uniqueResult1[0] + " : " + uniqueResult1[1] + " : " + uniqueResult1[2]);

        // HQL ile
        String hqlQuery2 = "FROM Student01 WHERE name = 'Cemil Bey'";
        Student01 uniqueResult2 = session.createQuery(hqlQuery2, Student01.class).uniqueResult();
        System.out.println(uniqueResult2);

        // !!! YUKARDAKI sorguyu HQL alias kullanarak yapalim
        String hqlQuery3 = "FROM Student01 std WHERE std.name='Cemil Bey'";
        Student01 uniqueResult3 =session.createQuery(hqlQuery3, Student01.class).uniqueResult();
        System.out.println(uniqueResult3);



        tx.commit();

        session.close();
        sf.close();

    }

}
