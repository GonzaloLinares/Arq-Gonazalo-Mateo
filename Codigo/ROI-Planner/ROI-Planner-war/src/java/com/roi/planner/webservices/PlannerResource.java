package com.roi.planner.webservices;

import com.roi.planner.businesslogic.SupplyPlanBeanLocal;

import javax.ejb.EJB;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("planner")
public class PlannerResource {

  @Context
  private UriInfo context;

  @EJB
  private SupplyPlanBeanLocal plannerBean;

  public PlannerResource() {
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSupplies() {
    try {
      return Response.ok(plannerBean.getSuppliesPlan()).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

  }
  
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSupply(@PathParam("id") String id) {
    try {
      Object obj = plannerBean.getSupplyPlan(id);
      return Response.ok(obj).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

  }

  @POST
  @Consumes("application/json")
  public Response postPlan(String createPlan) {
    try {
      plannerBean.createSupplyPlan(createPlan);
      return Response.ok("SupplyPlan creado con exito!").build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @POST
  @Path("/{id}")
  @Consumes("application/json")
  public Response postPlanByUri(@PathParam("id") String createPlan) {
    try {
      plannerBean.createSupplyPlan(createPlan);
      return Response.ok("SupplyPlan creado con exito!").build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @PUT
  @Consumes("application/json")
  public Response PutPlan(String modifyPlan) {

    try {
      plannerBean.modifySupplyPlan(modifyPlan);
      return Response.ok("SupplyPlan modificado con exito!").build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @PUT
  @Path("/{id}")
  @Consumes("application/json")
  public Response PutPlanByURI(@PathParam("id") String modifyPlan) {

    try {
      plannerBean.modifySupplyPlan(modifyPlan);
      return Response.ok("SupplyPlan modificado con exito!").build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response DeletePlan(@PathParam("id") String id) {
    try {
      plannerBean.deleteSupplyPlan(id);
      return Response.ok("SupplyPlan anulado con exito!").build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

}
