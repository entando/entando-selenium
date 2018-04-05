/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entando.selenium.tests;

import org.entando.selenium.pages.DTDashboardPage;
import org.entando.selenium.pages.DTLoginPage;
import org.entando.selenium.pages.DTUsersPage;
import org.entando.selenium.utils.FunctionalTest;
import org.entando.selenium.utils.ReceiptDTLoginPage;
import org.entando.selenium.utils.Utils;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author leobel
 */

public class DTUserEditProfileTest extends FunctionalTest {
    
    @Test
    public void runtTest(){
        DTLoginPage dtLoginPage = new DTLoginPage(driver);
        dtLoginPage.logIn("admin", "adminadmin");

        ReceiptDTLoginPage receiptDtPage = dtLoginPage.submit();
        assertTrue(receiptDtPage.isInitialized());
        
        DTDashboardPage dtDashboardPage = new DTDashboardPage(driver);
        dtDashboardPage.SelectSecondOrderLink("User Settings", "Users");
        
        DTUsersPage dtUsersPage = new DTUsersPage(driver);
        Utils util = new Utils();
        String user = "admin";
        Utils.Kebab kebab = util.getKebabOnTable(dtUsersPage.getUsersTable(), "Username", user, "button");
        kebab.getClickable().click();
        
        util.waitUntilVisible(driver, kebab.getActionList());
        
        util.clickKebabActionOnList(kebab.getActionList(), "Edit profile of: " + user);
    }
}
