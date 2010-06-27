package br.ufrj.dcc.comp2.databinding.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.zkoss.zkplus.jpa.JpaUtil;

import br.ufrj.dcc.comp2.databinding.model.Model;

public class BindingController {

	public <T extends Model> void addPerson(T entity) {
		EntityManager em = JpaUtil.getEntityManager();
		em.persist(entity);
	}

	public <T extends Model> T update(T selected) {
		EntityManager em = JpaUtil.getEntityManager();
		return em.merge(selected);
	}

	@SuppressWarnings("unchecked")
	public <T extends Model> List<T> getAll(Class<T> clazz) {
		EntityManager em = JpaUtil.getEntityManager();
		return em.createQuery("SELECT x FROM " + clazz.getName() + " x", clazz).getResultList();
	}

	public <T extends Model> T getById(Class<T> clazz, Integer id) {
		EntityManager em = JpaUtil.getEntityManager();
		return em.find(clazz, id);
	}

	public <T extends Model> void remove(T entity) {
		EntityManager em = JpaUtil.getEntityManager();
		em.remove(em.merge(entity));
	}
	
}
