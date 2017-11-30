package com.roi.supplies.businesslogic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.roi.logger.actionlog.ActionLogger;
import com.roi.logger.actionlog.LogFactory;
import com.roi.supplies.dataaccess.SuppliesDA;
import com.roi.supplies.domain.SupplyOrder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;


@Stateless
public class SuppliesBean implements SuppliesBeanLocal {

  @Inject
  @JMSConnectionFactory("jms/OrderPlanConnectionFactory")
  private JMSContext context;

  @Resource(lookup = "jms/OrderPlanQueue")
  private Queue queue;

  private final String authKey = "Salu2";
  private final String logName = "SupplyBean";
  private final Gson gson = new Gson();
  ActionLogger log = LogFactory.createLogger(logName);
  
  @EJB
  private SuppliesDA suppliesDA;

  @Override
  public String getSupply(String getSupply) throws Exception {
    try {
      log.logAction("Start supply order request");
      SupplyOrder getOrder = suppliesDA.get(gson.fromJson(getSupply, Long.class));
      if (getOrder != null) {
        log.logAction("Succesful supply order request");
        return gson.toJson(getOrder);
      } else {
        log.logAction("getSupply error: Order do not exist");
        throw new Exception("getSupply error: Order do not exist");
      }
    } catch (JsonSyntaxException ex) {
      log.logError("getSupply error: Could not cast fromJson properly");
      throw new Exception();
    } catch (Exception ex) {
      log.logError("getSupply error: " + ex.getMessage());
      throw new Exception();
    }
  }

  @Override
  public String getSupplies() throws Exception {
    try {
      log.logAction("Start all supplies order request");
      List<SupplyOrder> supplies = suppliesDA.getAll();
      log.logAction("Succesful all supplies order request");
      return gson.toJson(supplies);
    } catch (Exception ex) {
      log.logError("getSupplies error: " + ex.getMessage());
      throw new Exception();
    }
  }

  @Override
  public void postSupply(String newSupply) {
    try {
      log.logAction("Start post of supply order");
      SupplyOrder newOrder = gson.fromJson(newSupply, SupplyOrder.class);
      suppliesDA.add(newOrder);
      String orderId = newOrder.getId().toString();
      String validKey = getValidKey(authKey);
      SupplyOrderDTO supplyDto = entityToDto("CREATE#" + orderId + "#" + validKey);
      sendJMSMessageToOrderPlanQueue(supplyDto.getOrder());
      log.logAction("Succesful post of supply order");
    } catch (JsonSyntaxException ex) {
      log.logError("postSupply error: Could not cast fromJson properly");
    } catch (Exception ex) {
      log.logError("postSupply error: " + ex.getMessage());
    }
  }

  @Override
  public void putSupply(String putSupply)throws Exception {
    try {
      log.logAction("Start put of supply order");
      SupplyOrder supplyToModify = gson.fromJson(putSupply, SupplyOrder.class);
      suppliesDA.update(supplyToModify);

      String orderId = supplyToModify.getId().toString();
      if (orderId != null) {
        String validKey = getValidKey(authKey);
        SupplyOrderDTO supplyDto = entityToDto("MODIFY#" + orderId + "#" + validKey);
        sendJMSMessageToOrderPlanQueue(supplyDto.getOrder());
        log.logAction("Succesful put of supply order");
      } else {
        log.logError("putSupply error: Could not find Order to modify");
        throw new Exception("putSupply error: Could not find Order to modify"); 
      }
    } catch (JsonSyntaxException ex) {
      log.logError("putSupply error: Could not cast fromJson properly");
      throw new Exception("putSupply error: Could not cast fromJson properly"); 
    } catch (Exception ex) {
      log.logError("putSupply error: " + ex.getMessage());
      throw new Exception("deleteSupplyError: " + ex.getMessage()); 
    }
  }

  @Override
  public void deleteSupply(String deleteSupply)throws Exception {
    try {
      log.logAction("Start delete of supply order");
      SupplyOrder supplyToDelete = suppliesDA.get(gson.fromJson(deleteSupply, Long.class));
      suppliesDA.delete(supplyToDelete);

      String orderId = supplyToDelete.getId().toString();
      if (orderId != null) {
        String validKey = getValidKey(authKey);
        SupplyOrderDTO supplyDto = entityToDto("DELETE#" + orderId + "#" + validKey);
        sendJMSMessageToOrderPlanQueue(supplyDto.getOrder());
        log.logAction("Succesful delete of supply order");
      } else {
        log.logError("deleteSupplyError: Order to delete do not exist");
        throw new Exception("deleteSupplyError: Order to delete do not exist"); 
      }
    } catch (JsonSyntaxException ex) {
      log.logError("deleteSupply error: Could not cast fromJson properly");
      throw new Exception("deleteSupplyError: Order to delete do not exist"); 
    } catch (Exception ex) {
      log.logError("deleteSupply error: " + ex.getMessage());
      throw new Exception("deleteSupplyError: "+ ex.getMessage()); 
    }
  }

  private SupplyOrderDTO entityToDto(String action) throws Exception {
    try {
      SupplyOrderDTO orderDTO = new SupplyOrderDTO();
      orderDTO.setOrder(action);
      return orderDTO;
    } catch (Exception ex) {
      log.logError("entityToDto error: " + ex.getMessage());
      throw new Exception();
    }
  }

  private String getValidKey(String authkey) throws Exception {
    try {
      String response = "";

      String urlstr = "http://localhost:8080/ROI-Authenticator-war/getValidKey/" + authkey;
      URL url = new URL(urlstr);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String str;
      while ((str = in.readLine()) != null) {
        response += str + "\n";
      }
      in.close();
      
      return response;
    } catch (IOException en) {
      throw new Exception(en.getMessage());
    } catch (Exception ex) {
      throw new Exception(ex.getMessage());
    }
  }

  private void sendJMSMessageToOrderPlanQueue(String messageData) {
    try {
      log.logAction("Start sendJMSMessage to queue");
      context.createProducer().send(queue, messageData);
      log.logAction("Succesful sendJMSMessage to queue");
    } catch (JMSRuntimeException ex) {
      log.logError("sendJMSMessageToOrderPlanQueue error: Could not set JMSMessage to queue");
    } catch (Exception ex) {
      log.logError("sendJMSMessageToOrderPlanQueue error: " + ex.getMessage());
    }
  }
}
