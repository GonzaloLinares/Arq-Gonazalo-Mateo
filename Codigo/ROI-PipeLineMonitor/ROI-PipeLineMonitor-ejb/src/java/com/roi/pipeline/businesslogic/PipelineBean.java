package com.roi.pipeline.businesslogic;

import com.google.gson.Gson;

import com.roi.logger.actionlog.ActionLogger;

import com.roi.logger.actionlog.LogFactory;
import com.roi.pipelinemonitor.dataaccess.PipelineDA;
import com.roi.pipelinemonitor.domain.Pipeline;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PipelineBean implements PipelineBeanLocal {

  private final String logName = "PipelineMonitorBean";
  private final Gson gson = new Gson();
  ActionLogger log = LogFactory.createLogger(logName);

  @EJB
  private PipelineDA pipelineDA;

  @Override
  public String getAllPipelines() throws Exception {
    try {
      log.logAction("Start get all pipelines request");
      List<Pipeline> listPipelines = pipelineDA.getAll();
      log.logAction("Succesful get all pipelines request");
      return gson.toJson(listPipelines);
    } catch (Exception ex) {
      log.logError("getAllPipelines error: " + ex.getMessage());
      throw new Exception();
    }
  }

  @Override
  public String getPipeline(String jsonPipeline) throws Exception {
    try {
      log.logAction("Start get pipeline request");
      Pipeline getPipe = pipelineDA.getById(jsonPipeline);
      log.logAction("Succesful get all pipelines request");
      return gson.toJson(getPipe);
    } catch (IllegalArgumentException ex) {
      log.logError("getPipeline error: Argument received is not valid");
      throw new Exception();
    } catch (Exception ex) {
      log.logError("getPipeline error:" + ex.getMessage());
      throw new Exception();
    }
  }

  @Override
  public void createPipeline(String expectedValue) {
    try {
      log.logAction("Start post of pipeline");
      Pipeline newPipe = new Pipeline();
      newPipe.setExpectedValue(Integer.parseInt(expectedValue));
      pipelineDA.add(newPipe);
      log.logAction("Succesful post of pipeline");
    } catch (NumberFormatException ex) {
      log.logError("createPipeline error: Parameter must be a number");
    } catch (Exception ex) {
      log.logError("createPipeline error:" + ex.getMessage());
    }
  }

  @Override
  public void modifyPipeline() {
    throw new UnsupportedOperationException("modifyPipeline error: Operation not supported.");
  }

  @Override
  public void deletePipeline() {
    throw new UnsupportedOperationException("deletePipeline error: Operation not supported.");
  }
}
