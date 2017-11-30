/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.pipelinemonitor.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class Sensor implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private Pipeline pipelineId;
  private double pressureValue;

  @Temporal(javax.persistence.TemporalType.DATE)
  private Date valueDate;

  public Sensor() {
  }

  public Pipeline getPipelineId() {
    return pipelineId;
  }

  public void setPipelineId(Pipeline pipelineId) {
    this.pipelineId = pipelineId;
  }

  public double getPressureValue() {
    return pressureValue;
  }

  public void setPressureValue(double pressureValue) {
    this.pressureValue = pressureValue;
  }

  public Date getValueDate() {
    return valueDate;
  }

  public void setValueDate(Date valueDate) {
    this.valueDate = valueDate;
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
    if (!(object instanceof Sensor)) {
      return false;
    }
    Sensor other = (Sensor) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.roi.pipelinemonitor.domain.Sensor[ id=" + id + " ]";
  }

}
