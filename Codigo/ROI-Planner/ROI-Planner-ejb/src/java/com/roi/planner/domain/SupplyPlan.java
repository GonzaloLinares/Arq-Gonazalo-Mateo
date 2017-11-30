package com.roi.planner.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class SupplyPlan implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private boolean eliminated;
  private int orderId;
  @OneToMany(cascade = CascadeType.ALL)
  private List<NetworkSection> sectionLst;

  public SupplyPlan() {
    eliminated = false;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public List<NetworkSection> getSection() {
    return sectionLst;
  }

  public void setSection(List<NetworkSection> section) {
    this.sectionLst = section;
  }

  public boolean isEliminated() {
    return eliminated;
  }

  public void setEliminated(boolean eliminated) {
    this.eliminated = eliminated;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof SupplyPlan)) {
      return false;
    }
    SupplyPlan other = (SupplyPlan) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.roi.planner.domain.SupplyPlan[ id=" + id + " ]";
  }

}
