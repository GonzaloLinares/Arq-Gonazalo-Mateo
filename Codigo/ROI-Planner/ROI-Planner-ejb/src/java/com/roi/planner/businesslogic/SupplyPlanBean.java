/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.planner.businesslogic;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.roi.logger.actionlog.ActionLogger;
import com.roi.logger.actionlog.LogFactory;
import com.roi.planner.dataaccess.PlannerDA;
import com.roi.planner.domain.NetworkSection;
import com.roi.planner.domain.SupplyPlan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SupplyPlanBean implements SupplyPlanBeanLocal {

  private final String logName = "PlanBean";
  private final Gson gson = new Gson();
  ActionLogger log = LogFactory.createLogger(logName);

  @EJB
  private PlannerDA planner;

  @Override
  public void createSupplyPlan(String createPlan) {
    try {
      log.logAction("Start creation of SupplyPlan with orderId " + createPlan);
      SupplyPlan newPlan = new SupplyPlan();
      newPlan.setOrderId(Integer.parseInt(createPlan));
      List<NetworkSection> sectionList = getNetworkSections();
      newPlan.setSection(sectionList);
      planner.add(newPlan);
      log.logAction("Created SupplyPlan wirh orderId " + createPlan);
    } catch (NumberFormatException ex) {
      log.logError("CreateSupplyPlan error: Parameter must be a number");
    } catch (Exception ex) {
      log.logError("CreateSupplyPlan error: " + ex.getMessage());
    }
  }

  @Override
  public void modifySupplyPlan(String modifyPlan) {
    try {
      log.logAction("Start modification of SupplyPlan " + modifyPlan);
      SupplyPlan newPlan = planner.getByOrderId(modifyPlan);
      List<NetworkSection> sectionList = getNetworkSections();
      planner.delete(newPlan);
      newPlan.setSection(sectionList);
      planner.add(newPlan);
      log.logAction("Modified SupplyPlan " + modifyPlan);
    } catch (IndexOutOfBoundsException ex) {
      log.logError("modifySupplyPlan error: Element do not exist");
    } catch (Exception ex) {
      log.logError("modifySupplyPlan error: " + ex.getMessage());
    }
  }

  @Override
  public void deleteSupplyPlan(String deletePlan) {
    try {
      log.logAction("Start elimination of SupplyPlan " + deletePlan);
      SupplyPlan getOrder = planner.getByOrderId(deletePlan);
      getOrder.setEliminated(true);
      planner.update(getOrder);
      log.logAction("Eliminated SupplyPlan " + deletePlan);
    } catch (Exception ex) {
      log.logError("deleteSupplyPlan error: " + deletePlan);
    }
  }

  @Override
  public String getSuppliesPlan() {
    try {
      log.logAction("Start solicitation collection of SupplyPlan");
      List<SupplyPlan> listPlans = planner.getAll();
      log.logAction("Returned collection of SupplyPlan");
      return gson.toJson(listPlans);
    } catch (Exception ex) {
      log.logError("getSuppliesPlan error: " + ex.getMessage());
      return "";
    }
  }

  private List<NetworkSection> getNetworkSections() {
    try {
      log.logAction("Start request for new NetworkSecction");
      String response = "";
      URL url = new URL("https://pipeline-calculator-api.herokuapp.com/pipeline-route/service/1");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String str;
      while ((str = in.readLine()) != null) {
        response += str + "\n";
      }
      in.close();
      JSONObject listObjact = new JSONObject(response);
      JSONArray networkArray = new JSONArray(listObjact.getString("sections"));
      ArrayList<NetworkSection> newSection = new ArrayList<>();
      for (int i = 0; i < networkArray.length(); i++) {
        JSONObject networkJson = networkArray.getJSONObject(i);
        String json = networkJson.toString();
        NetworkSection section = gson.fromJson(json, NetworkSection.class);
        newSection.add(section);
      }
      log.logAction("Got new NetworkSection succesfully");
      return newSection;
    } catch (IOException en) {
      log.logError("getNetworkSection error: Could not connect with api ");
      return new ArrayList<>();
    } catch (JSONException en) {
      log.logError("getNetworkSection error: Could not crete JSONObject");
      return new ArrayList<>();
    } catch (Exception ex) {
      log.logError("getNetworkSection error: " + ex.getMessage());
      return new ArrayList<>();
    }
  }

  @Override
  public String getSupplyPlan(String supplyPlan) throws Exception {
    try {
      log.logAction("Start supply plan request");
      SupplyPlan getPlan = planner.get(gson.fromJson(supplyPlan, Long.class));
      if (getPlan != null) {
        log.logAction("Succesful supply plan request");
        return gson.toJson(getPlan);
      } else {
        log.logAction("getSupplyPlan error: Plan do not exist");
        throw new Exception("getSupplyPlan error: Plan do not exist");
      }
    } catch (JsonSyntaxException ex) {
      log.logError("getSupplyPlan error: Could not cast fromJson properly");
      throw new Exception();
    } catch (Exception ex) {
      log.logError("getSupplyPlan error: " + ex.getMessage());
      throw new Exception();
    }
  }
}
