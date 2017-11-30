/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.pipeline.businesslogic;

import javax.ejb.Local;

@Local
public interface SensorBeanLocal {

  public String getSensor(String id) throws Exception;

  public String getSensors() throws Exception;

  public void createSensor(String jsonSensor) throws Exception;

  public void modifySensor(String jsonSensor);

  public void deleteSensor(String jsonSensor);

  public void evaluateInfo(String jsonInfo) throws Exception;
}
