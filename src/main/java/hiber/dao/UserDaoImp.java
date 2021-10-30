package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      User resultUser = null;
      String hql = "from User us left join fetch us.car where us.car.model =:model and us.car.series =:series";
      try (Session sess = sessionFactory.openSession()) {
         TypedQuery<User> query = sess.createQuery(hql);
         query.setParameter("model", model);
         query.setParameter("series", series);
         resultUser = query.getSingleResult();
      } catch (PersistenceException e) {
         e.printStackTrace();
      }
      return resultUser;
   }

}
