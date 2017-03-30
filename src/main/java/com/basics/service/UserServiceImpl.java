package com.basics.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.basics.exception.DbException;
import com.basics.model.User;


public class UserServiceImpl implements UserService {
	public Logger log = LogManager.getLogger(com.basics.service.UserServiceImpl.class);

	@PersistenceContext
    private EntityManager em;
	
	@Transactional(readOnly=true)
	public List<User> getAllUsers() {
		CriteriaBuilder critiriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<User> criteriaQuery = critiriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		TypedQuery<User> typedQuery = em.createQuery(criteriaQuery);
		
		
		return typedQuery.getResultList();
	}
	
	@Transactional(readOnly=true)
	public List<User> search(String critere,String property) {
		Criteria c = createCriteria();
		c.add(Restrictions.like(critere, property+"%"));
		try {
			return c.list();
		} catch (HibernateException e) {
			throw new DbException("search", e);
		}
	}
	
	@Transactional(readOnly=true)
	public User getUser(int id) {
		try {
			return em.find(User.class, id);
		} catch (HibernateException e) {
			throw new DbException("search", e);
		}
	}
	
	@Transactional
	public void createUser(final User user) {
		try {
			em.persist(user);
		} catch (HibernateException e) {
			throw new DbException("search", e);
		}
	}
	
	@Transactional
	public void updateUser(final User user) {
		try {
			em.merge(user);
		} catch (HibernateException e) {
			throw new DbException("search", e);
		}
	}
	
	@Transactional
	public void deleteUser(final User user) {
		try {
			em.remove(user);
		} catch (HibernateException e) {
			throw new DbException("search", e);
		}
	}
	
	public Criteria createCriteria() {
		try {
			Session session = em.unwrap(Session.class);
			return session.createCriteria(User.class);
		} catch (HibernateException e) {
			throw new DbException("CreateCriteria", e);
		}
	}
	
}
