/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.pipelinemonitor.dataaccess;

import com.roi.pipelinemonitor.domain.Sensor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@Stateless
public class SensorDA extends Repository<Sensor> {

  @PersistenceContext
  private EntityManager em;

  public SensorDA() {
    super(Sensor.class);
  }

  public Sensor getById(String sensorId) {
    return em.find(Sensor.class, Long.parseLong(sensorId));
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }
}
