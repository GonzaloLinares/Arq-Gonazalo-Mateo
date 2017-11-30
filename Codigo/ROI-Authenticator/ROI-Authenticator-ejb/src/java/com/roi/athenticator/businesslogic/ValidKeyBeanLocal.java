/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.roi.athenticator.businesslogic;

import javax.ejb.Local;


@Local
public interface ValidKeyBeanLocal {

  public String getValidKey(String authKey) throws Exception;

  public boolean checkValidKey(String validKey);

  public void deleteKey(String deleteKey);

}
