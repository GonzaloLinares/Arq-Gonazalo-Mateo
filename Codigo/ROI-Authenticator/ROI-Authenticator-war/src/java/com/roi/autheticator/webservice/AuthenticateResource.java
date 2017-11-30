/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.autheticator.webservice;

import com.roi.athenticator.businesslogic.ValidKeyBeanLocal;

import javax.ejb.EJB;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("")
@RequestScoped
public class AuthenticateResource {

  @Context
  private UriInfo context;

  @EJB
  private ValidKeyBeanLocal validKeyBean;

  public AuthenticateResource() {
  }

  @POST
  @Path("/getValidKey/{key}")
  @Consumes("application/json")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getValidKey(@PathParam("key") String authKey) {
    try {

      String validKey = validKeyBean.getValidKey(authKey);
      return Response.ok(validKey).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

  }

  @POST
  @Path("/checkValidKey/{key}")
  @Consumes("application/json")
  public Response checkValidKey(@PathParam("key") String key) {
    try {
      boolean checkKey = validKeyBean.checkValidKey(key);
      if (checkKey) {
        validKeyBean.deleteKey(key);
      }
      return Response.ok(checkKey).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }
}
