/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.authenticator.dataaccess;

import com.roi.authenticator.domain.ValidKey;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ValidKeyDA extends Repository<ValidKey> {

  @PersistenceContext
  private EntityManager em;

  public ValidKeyDA() {
    super(ValidKey.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public ValidKey getById(String key) {
    List<ValidKey> query = (List<ValidKey>) getEntityManager()
            .createQuery("SELECT a FROM ValidKey a WHERE a.authKey = :key")
            .setParameter("key", key)
            .getResultList();
    return query.get(0);
  }
}
