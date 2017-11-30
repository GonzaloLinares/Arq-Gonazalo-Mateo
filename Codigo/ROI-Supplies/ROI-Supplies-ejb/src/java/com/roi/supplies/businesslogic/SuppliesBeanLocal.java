package com.roi.supplies.businesslogic;

import javax.ejb.Remote;


@Remote
public interface SuppliesBeanLocal {

  public String getSupply(String getSupply) throws Exception;

  public String getSupplies() throws Exception;

  public void postSupply(String newSupply);

  public void putSupply(String putSupply)throws Exception;

  public void deleteSupply(String deleteSupply) throws Exception;
}
