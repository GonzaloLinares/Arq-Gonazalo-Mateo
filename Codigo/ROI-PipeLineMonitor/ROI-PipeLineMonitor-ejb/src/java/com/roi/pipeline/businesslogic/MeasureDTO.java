/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.pipeline.businesslogic;

import java.util.Date;

public class MeasureDTO {

  private Long sensorId;
  private Long pipelineId;
  private double value;
  private Date date;

  public MeasureDTO() {
  }

  public Long getSensorId() {
    return sensorId;
  }

  public void setSensorId(Long sensorId) {
    this.sensorId = sensorId;
  }

  public Long getPipielineId() {
    return pipelineId;
  }

  public void setPipielineId(Long pipielineId) {
    this.pipelineId = pipielineId;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

}
