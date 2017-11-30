/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.pipelinemonitor.webservices;

import com.roi.pipeline.businesslogic.PipelineBeanLocal;

import javax.ejb.EJB;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("pipeline")
public class PipelineResource {

  @Context
  private UriInfo context;

  @EJB
  private PipelineBeanLocal pipelineBean;

  public PipelineResource() {
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllPipeline() {
    try {
      return Response.ok(pipelineBean.getAllPipelines()).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @POST
  public Response postPipeline(String expectedValue) {
    try {

      pipelineBean.createPipeline(expectedValue);
      return Response.ok("Pipeline creado con exito!").build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

}
