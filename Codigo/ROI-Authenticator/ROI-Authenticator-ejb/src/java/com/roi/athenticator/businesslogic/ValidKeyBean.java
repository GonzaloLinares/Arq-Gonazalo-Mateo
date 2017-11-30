/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.athenticator.businesslogic;

import com.google.gson.Gson;

import com.roi.authenticator.dataaccess.ValidKeyDA;
import com.roi.authenticator.domain.ValidKey;
import com.roi.logger.actionlog.ActionLogger;
import com.roi.logger.actionlog.LogFactory;



import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;


@Stateless
public class ValidKeyBean implements ValidKeyBeanLocal {

  @EJB
  private ValidKeyDA keyDA;
  private final String authToken = "Salu2";
  private final String logName = "Authenticator";
  
  ActionLogger log = LogFactory.createLogger(logName);

  Gson gson = new Gson();

  @Override
  public String getValidKey(String verifyUuid) throws Exception {
    try {
      log.logAction("Authentication: started process of getting new key");
      String msg = gson.fromJson(verifyUuid, String.class);
      if (msg.equals(authToken)) {
        UUID uuid = UUID.randomUUID();
        ValidKey newKey = new ValidKey();
        newKey.setAuthKey(uuid.toString());
        keyDA.add(newKey);
        log.logAction("Authentication: started process of getting new key");
        return uuid.toString();
      } else {
        log.logError("Authentication error: failed to get valid key");
        throw new Exception("Authentication error: failed to get valid key");
      }
    } catch (Exception e) {
      log.logError("Authentication error: failed to get valid key");
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public boolean checkValidKey(String key) {
    log.logAction("Authentication: checking if key is valid");
    String strKey = gson.fromJson(key, String.class);
    boolean result = keyDA.getById(strKey) != null;
    log.logAction("Authentication: key result was " + result);
    return result;
  }

  @Override
  public void deleteKey(String key) {
    log.logAction("Authentication: deleting last valid key");
    String strKey = gson.fromJson(key, String.class);
    ValidKey deleteKey = keyDA.getById(strKey);
    keyDA.delete(deleteKey);
    log.logAction("Authentication: deleting last valid key succesfull");
  }
}
