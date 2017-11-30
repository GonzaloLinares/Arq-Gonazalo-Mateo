/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.pipeline.businesslogic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.roi.logger.actionlog.ActionLogger;
import com.roi.logger.actionlog.LogFactory;
import com.roi.pipelinemonitor.dataaccess.PipelineDA;
import com.roi.pipelinemonitor.dataaccess.SensorDA;
import com.roi.pipelinemonitor.domain.Pipeline;
import com.roi.pipelinemonitor.domain.Sensor;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;


@Stateless
public class SensorBean implements SensorBeanLocal {

  private final String logSensor = "PipelineMonitorBean";
  private final Gson gson = new Gson();
  ActionLogger log = LogFactory.createLogger(logSensor);

  @EJB
  private SensorDA sensorDA;

  @EJB
  private PipelineDA pipelineDA;

  @Override
  public String getSensor(String id) throws Exception {
    try {
      Sensor getSensor = sensorDA.getById(id);
      return gson.toJson(getSensor);
    } catch (IllegalArgumentException ex) {
      log.logError("getSensors error: Argument received is not valid");
      throw new Exception();
    } catch (Exception ex) {
      log.logError("getSensor error:" + ex.getMessage());
      throw new Exception();
    }
  }

  @Override
  public String getSensors() throws Exception {
    try {
      log.logAction("Start get sensor request");
      List<Sensor> listSensor = sensorDA.getAll();
      log.logAction("Succesful get sensor request");
      return gson.toJson(listSensor);
    } catch (Exception ex) {
      log.logError("getSensors error:" + ex.getMessage());
      throw new Exception();
    }
  }

  @Override
  public void createSensor(String jsonSensor) throws Exception {
    try {
      log.logAction("Start post of sensor");
      Sensor newSensor = new Sensor();
      Pipeline sensorPipeline = pipelineDA.getById(jsonSensor);
      newSensor.setPipelineId(sensorPipeline);
      newSensor.setPressureValue(0);
      Date now = new Date();
      newSensor.setValueDate(now);
      if (sensorPipeline != null) {
        sensorDA.add(newSensor);
        log.logAction("Succesful post of sensor");
      } else {
        log.logError("Error CreateSensor: Do no exist pipeline");
        throw new Exception("Do not exist pipeline assigned");
      }
    } catch (IllegalArgumentException ex) {
      log.logError("getSensors error: Argument received is not valid");
      throw new Exception("Argument received is not valid");
    } catch (Exception ex) {
      log.logError("getSensors error:" + ex.getMessage());
      throw new Exception(ex.getMessage());
    }
  }

  @Override
  public void modifySensor(String jsonSensor) {
    throw new UnsupportedOperationException("modifySensor error: Operation not supported.");
  }

  @Override
  public void deleteSensor(String jsonSensor) {
    throw new UnsupportedOperationException("deleteSensor error: Operation not supported.");
  }

  @Override
  public void evaluateInfo(String jsonInfo) throws Exception {
    try {
      MeasureDTO measure = gson.fromJson(jsonInfo, MeasureDTO.class);
      int id = Integer.parseInt(measure.getSensorId().toString());
      Sensor sen = sensorDA.getById(id + "");
      if (sen == null) {
        throw new Exception("Could not find sensor");
      }
      if (!sen.getPipelineId().getId().equals(measure.getPipielineId())) {
        throw new Exception("Sensor is from a different pipeline ");
      }
      Pipeline pip = pipelineDA.get(sen.getPipelineId().getId());
      double expectedValue = pip.getExpectedValue();
      double value = measure.getValue();
      if ((expectedValue * 1.01) < value || (expectedValue * 0.99) > value) {
        registerError(value, pip.getId(), sen.getId(), measure.getDate(), expectedValue);
      }
    } catch (JsonSyntaxException ex) {
      log.logError("evaluateInfo error: Could not cast fromJson properly");
    } catch (Exception ex) {
      log.logError("evaluteInfo error: " + ex.getMessage());
    }
  }

  private void registerError(double value, Long idPip, Long idSen,
          Date date, double expectedValue) {
    log.logAction(date + " - Se detecto al sensor " + idSen 
            + " en la tubería con id " + idPip + " donde registro una medida de valor "
            + value + " y se esperaba que estuviera cerca de " + expectedValue);
  }
}
