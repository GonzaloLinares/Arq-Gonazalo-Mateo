package com.roi.supplies.businesslogic;

import java.io.Serializable;


public class SupplyOrderDTO implements Serializable {

  private String order;

  public SupplyOrderDTO() {
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }
}
