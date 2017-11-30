/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.pipelinemonitor.webservices;

import com.roi.pipeline.businesslogic.SensorBeanLocal;

import javax.ejb.EJB;

import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Gonzalo
 */
@Path("sensor")
public class SensorResource {

    @Context
    private UriInfo context;
    
    @EJB
    private SensorBeanLocal sensorBean;
    
    public SensorResource() {
    }
    
    
    @GET
    public Response getSensors()
    {   
        try
        {                  
            return Response.ok(sensorBean.getSensors()).build();
        }catch(Exception e)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }  
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensor(@PathParam("id")String id)
    {     
        try
        {
            return Response.ok(sensorBean.getSensor(id)).build();
        }catch(Exception e)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    public Response postSensor(String sensor)
    {
        try
        {
            sensorBean.createSensor(sensor);
            return Response.ok("Sensor creado con exito!").build();
        }
        catch(Exception e)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("/report")
    @Consumes("application/json")
    public Response postInformation(String info)
    {
        try
        {
            sensorBean.evaluateInfo(info);
            return Response.ok("Informacion evaluada.").build();
        }
        catch(Exception e)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    } 

}
