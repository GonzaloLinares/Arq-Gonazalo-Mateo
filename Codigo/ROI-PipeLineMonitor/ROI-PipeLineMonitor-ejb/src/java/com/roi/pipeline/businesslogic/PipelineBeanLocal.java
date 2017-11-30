/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.pipeline.businesslogic;

import javax.ejb.Local;

@Local
public interface PipelineBeanLocal {

  public String getAllPipelines() throws Exception;

  public String getPipeline(String jsonPipeline) throws Exception;

  public void createPipeline(String averageValue);

  public void modifyPipeline();

  public void deletePipeline();
}
