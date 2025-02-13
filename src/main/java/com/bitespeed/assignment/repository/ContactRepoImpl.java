package com.bitespeed.assignment.repository;

import com.bitespeed.assignment.models.Contact;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ContactRepoImpl implements ContactRepo {

    @Autowired
    private LocalSessionFactoryBean localSessionFactoryBean;

    private Session getCurrentSession(){
        return localSessionFactoryBean.getObject().getCurrentSession();
    }


    @Override
    @Transactional
    public void save(Contact contact) {
        try {
            Session session = getCurrentSession();
            session.save(contact);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void update(Contact contact) {
        try {
            Session session = getCurrentSession();
            session.update(contact);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<Contact> getAllContacts() {
        try {
            Session session = getCurrentSession();
            String hql = "FROM Contact";
            Query<Contact> query = session.createQuery(hql, Contact.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public List<Contact> findAllByIds(ArrayList<Long> ids) {
        try {
            Session session = getCurrentSession();
            String hql = "FROM Contact WHERE id IN (:ids)";
            Query<Contact> query = session.createQuery(hql, Contact.class);
            query.setParameter("ids", ids);
            return query.getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public List<Contact> findByEmailOrPhoneNumber(String email, String phoneNumber) {
        try {
            Session session = getCurrentSession();
            String hql = "FROM Contact c WHERE c.email = :email OR c.phoneNumber = :phoneNumber";
            Query<Contact> query = session.createQuery(hql, Contact.class);
            query.setParameter("email", email);
            query.setParameter("phoneNumber", phoneNumber);
            return query.getResultList();
        }
        catch (NoResultException e1){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public List<Contact> findAllByGroup(Long primaryId) {
        try {
            Session session = getCurrentSession();
            String hql = "FROM Contact c WHERE c.id = :primaryId OR c.linkedId = :primaryId";
            Query<Contact> query = session.createQuery(hql, Contact.class);
            query.setParameter("primaryId", primaryId);
            return query.getResultList();
        }
        catch (NoResultException e1){
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
