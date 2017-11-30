package com.roi.supplies.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class SupplyOrder implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long clientId;
  @Temporal(javax.persistence.TemporalType.DATE)
  private Date supplyingStart;
  private int volumeContracted;
  private Long serviceEndPointId;
  private int payDay;

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public Date getSupplyingStart() {
    return supplyingStart;
  }

  public void setSupplyingStart(Date supplyingStart) {
    this.supplyingStart = supplyingStart;
  }

  public int getVolumeContracted() {
    return volumeContracted;
  }

  public void setVolumeContracted(int volumeContracted) {
    this.volumeContracted = volumeContracted;
  }

  public Long getServiceEndPointId() {
    return serviceEndPointId;
  }

  public void setServiceEndPointId(Long serviceEndPointId) {
    this.serviceEndPointId = serviceEndPointId;
  }

  public int getPayDay() {
    return payDay;
  }

  public void setPayDay(int payDay) {
    this.payDay = payDay;
  }

  public SupplyOrder() {
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
    if (!(object instanceof SupplyOrder)) {
      return false;
    }
    SupplyOrder other = (SupplyOrder) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.roi.supplies.domain.SupplyOrder[ id=" + id + " ]";
  }

}
