package com.roi.planner.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class NetworkSection implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String sectionId;
  private String sourceId;
  private String actuatorId;

  public String getSourceId() {
    return sourceId;
  }

  public void setSourceId(String endPointId) {
    this.sourceId = endPointId;
  }

  public String getActuatorId() {
    return actuatorId;
  }

  public void setActuatorId(String actuatorId) {
    this.actuatorId = actuatorId;
  }

  public NetworkSection() {
  }

  public String getSectionId() {
    return sectionId;
  }

  public void setSectionId(String id) {
    this.sectionId = id;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (sectionId != null ? sectionId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof NetworkSection)) {
      return false;
    }
    NetworkSection other = (NetworkSection) object;
    if ((this.sectionId == null && other.sectionId != null) 
         || (this.sectionId != null && !this.sectionId.equals(other.sectionId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.roi.planner.domain.NetworkSection[ sectionId=" + sectionId + " ]";
  }

}
