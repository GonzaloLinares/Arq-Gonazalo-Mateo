
package com.roi.supplies.dataaccess;

import com.roi.supplies.domain.SupplyOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class SuppliesDA extends Repository<SupplyOrder> {

  @PersistenceContext
  private EntityManager Em;

  public SuppliesDA() {
    super(SupplyOrder.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return Em;
  }
}
