package main;

import org.hibernate.Session;
import org.hibernate.Transaction;
import user_profile.User;

public class Main {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            User user = User.create();
            user.setUsername("hello");
            user.setPassword("world");
            session.save(user);

            transaction.commit();
        }

        catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
