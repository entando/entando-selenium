/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.entando.selenium.tests;

import org.entando.selenium.pages.DTDashboardPage;
import org.entando.selenium.pages.DTLoginPage;
import org.entando.selenium.pages.DTUserDetailsPage;
import org.entando.selenium.pages.DTUsersPage;
import org.entando.selenium.utils.FunctionalTest;
import org.entando.selenium.utils.ReceiptDTLoginPage;
import org.entando.selenium.utils.Utils;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author leobel
 */
public class DTUserDetailsTest extends FunctionalTest {
    
    @Test
    public void runTest(){
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
        
        util.waitUntilIsVisible(driver, kebab.getActionList());
        
        util.clickKebabActionOnList(kebab.getActionList(), "View profile of: " + user);
        
        DTUserDetailsPage dtUserDetailsPage = new DTUserDetailsPage(driver);
        
        String pageTitle = "Details";
        String[] headers = new String[]{"Username", "Full Name", "Email"}; 
        
        Assert.assertEquals(pageTitle, dtUserDetailsPage.getPageTitle().getText());
        Assert.assertArrayEquals(headers, dtUserDetailsPage.getDetailsTableHeaders());
        Assert.assertTrue(dtUserDetailsPage.getBackButton().isDisplayed());
    }
}
