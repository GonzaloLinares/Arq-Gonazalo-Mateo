/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roi.authenticator.dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public abstract class Repository<T> {

  private final Class<T> entityClass;

  public Repository(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  protected abstract EntityManager getEntityManager();

  public void add(T entity) {
    getEntityManager().persist(entity);
  }

  public void delete(T entity) {
    getEntityManager().remove(getEntityManager().merge(entity));
  }

  public T get(Object id) {
    return getEntityManager().find(entityClass, id);
  }

  public List<T> getAll() {
    CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    cq.select(cq.from(entityClass));
    return getEntityManager().createQuery(cq).getResultList();
  }
}
