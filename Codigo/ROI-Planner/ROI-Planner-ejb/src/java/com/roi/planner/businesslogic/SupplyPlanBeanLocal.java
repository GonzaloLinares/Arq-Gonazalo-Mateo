package com.roi.planner.businesslogic;

import javax.ejb.Local;

@Local
public interface SupplyPlanBeanLocal {

  public String getSupplyPlan(String supplyPlan) throws Exception;
  
  public String getSuppliesPlan() throws Exception;

  public void createSupplyPlan(String createPlan);

  public void modifySupplyPlan(String modifyPlan);

  public void deleteSupplyPlan(String deletePlan);
}
