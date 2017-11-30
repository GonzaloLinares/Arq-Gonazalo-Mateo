package com.roi.planner.dataaccess;

import com.roi.planner.domain.SupplyPlan;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PlannerDA extends Repository<SupplyPlan> {

  @PersistenceContext
  private EntityManager em;

  public PlannerDA() {
    super(SupplyPlan.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }
}
