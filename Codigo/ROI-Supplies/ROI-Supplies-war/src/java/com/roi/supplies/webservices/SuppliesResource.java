package com.roi.supplies.webservices;

import com.roi.supplies.businesslogic.SuppliesBeanLocal;

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


@Path("supplies")
public class SuppliesResource {

  @Context
  private UriInfo context;

  @EJB
  private SuppliesBeanLocal supplyBean;

  public SuppliesResource() {
  }

  @GET
  public Response getSupplying() {
    try {
      return Response.ok(supplyBean.getSupplies()).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSupply(@PathParam("id") String id) {
    try {
      return Response.ok(supplyBean.getSupply(id)).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @POST
  @Consumes("application/json")
  public Response postSupplying(String supplyOrder) {
    try {
      supplyBean.postSupply(supplyOrder);
      return Response.ok("SupplyOrder creado con exito!").build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @PUT
  @Consumes("application/json")
  public Response putSupplying(String supplyOrder) {
    try {
      supplyBean.putSupply(supplyOrder);
      return Response.ok("SupplyOrder modificado con exito!").build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @DELETE
  @Path("/{id}")
  public Response deleteSupplying(@PathParam("id") String id) {
    try {
      supplyBean.deleteSupply(id);
      return Response.ok("SupplyOrder eliminado con exito!").build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }
}
