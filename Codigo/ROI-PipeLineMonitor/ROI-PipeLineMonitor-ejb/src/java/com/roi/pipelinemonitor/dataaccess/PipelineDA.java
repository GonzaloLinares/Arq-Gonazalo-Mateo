/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.pipelinemonitor.dataaccess;

import com.roi.pipelinemonitor.domain.Pipeline;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PipelineDA extends Repository<Pipeline> {

  @PersistenceContext
  private EntityManager em;

  public PipelineDA() {
    super(Pipeline.class);
  }

  public Pipeline getById(String pipelineId) {
    return em.find(Pipeline.class, Long.parseLong(pipelineId));
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

}
