/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.planner.queue;

import com.google.gson.Gson;

import com.roi.logger.actionlog.ActionLogger;
import com.roi.logger.actionlog.LogFactory;
import com.roi.planner.businesslogic.SupplyPlanBeanLocal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;


@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup",
    propertyValue = "jms/OrderPlanQueue"),
    @ActivationConfigProperty(propertyName = "destinationType",
    propertyValue = "javax.jms.Queue")
})
public class OrderPlanQueue implements MessageListener {

  ActionLogger log = LogFactory.createLogger("QueueReports");

  @EJB
  private SupplyPlanBeanLocal plannerBean;

  Gson gson = new Gson();

  public OrderPlanQueue() {
  }

  @Override
  public void onMessage(Message message) {
    String msj;

    try {
      msj = message.getBody(String.class);
      String[] actionId = msj.split("#");
      if (checkValidKey(actionId[2])) {
        switch (actionId[0]) {
          case "CREATE":
            plannerBean.createSupplyPlan(actionId[1]);
            break;
          case "DELETE":
            plannerBean.deleteSupplyPlan(actionId[1]);
            break;
          case "MODIFY":
            plannerBean.modifySupplyPlan(actionId[1]);
            break;
          default:
            break;
        }
      } else {
        log.logError("Queue error: Authorization key is not valid");
      }
    } catch (JMSException ex) {
      log.logError("Queue error: " + ex);
    } catch (Exception e) {
      log.logError("Queue error: " + e);
    }
  }

  public boolean checkValidKey(String keyToCheck) throws Exception {
    try {
      String response = "";
      URL url = new URL("http://localhost:8080/ROI-Authenticator-war/checkValidKey/" + keyToCheck);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String str;
      while ((str = in.readLine()) != null) {
        response += str + "\n";
      }
      in.close();

      String key = gson.fromJson(response, String.class);
      return key.equals("true");
    } catch (IOException en) {
      throw new Exception(en.getMessage());
    } catch (Exception ex) {
      throw new Exception(ex.getMessage());
    }
  }
}
