/*
Copyright 2015-Present Entando Inc. (http://www.entando.com) All rights reserved.
This library is free software; you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation; either version 2.1 of the License, or (at your option)
any later version.
This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.
 */
package org.entando.selenium.tests;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.entando.selenium.pages.DTDashboardPage;
import org.entando.selenium.pages.DTFileBrowserCreateTextFilePage;
import org.entando.selenium.pages.DTFileBrowserPage;
import org.entando.selenium.testHelpers.FileBrowserTestBase;
import org.entando.selenium.utils.Parallelized;
import org.entando.selenium.utils.Utils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;


/**
 * This class perform a test of the Create a text file in File Browser page
 * 
 * @version 1.03
 */
@RunWith(Parallelized.class)
public class DTFileBrowserCreateTextFileTest extends FileBrowserTestBase{
    
    public DTFileBrowserCreateTextFileTest(String browserName, String platformName) {
        super(browserName, platformName);
    }
    
    /*
        Test
    */  
    @Test
    public void runTest() throws InterruptedException {
        /*
            Pages used on this test
        */
        DTDashboardPage dTDashboardPage = new DTDashboardPage(driver);
        DTFileBrowserPage dTFileBrowserPage = new DTFileBrowserPage(driver);
        DTFileBrowserCreateTextFilePage dTFileBrowserCreateTextFilePage =
                new DTFileBrowserCreateTextFilePage(driver);
        
        /*
            Parameters
        */
        //Link menù buttons
        String firstLevelLink = "Configuration";
        String secondLevelLink = "File browser";
        
        //Final page title
        String pageTitle = "Create text file";
        
        /*
            Actions and asserts
        */
        //Login
        login();
        
        //Navigation to the page
        dTDashboardPage.SelectSecondOrderLinkWithSleep(firstLevelLink, secondLevelLink);
        
        //Click on public folder
        WebElement link = dTFileBrowserPage.getTable().getLinkOnTable(publicFolder, 0, 0);
        Assert.assertFalse("Can't find " + publicFolder + "in the table",
                link == null);
        link.click();
        
        //Wait loading page
        Utils.waitUntilIsPresent(driver, dTFileBrowserPage.spinnerTag);
        Utils.waitUntilIsDisappears(driver, dTFileBrowserPage.spinnerTag);
        Utils.waitUntilIsVisible(driver, dTFileBrowserPage.getUploadButton());
        
        //Click on Create text file button
        dTFileBrowserPage.getCreateTextFileButton().click();
        
        //Wait loading page
        Utils.waitUntilIsVisible(driver, dTFileBrowserCreateTextFilePage.getSaveButton());
        
        //Asserts the PAGE TITLE is the expected one
        Assert.assertEquals("Page title is incorrect", pageTitle, 
                Utils.trimInitialSpaces(dTFileBrowserPage.getPageTitle().getText()));        
        
        //Asserts the presence of the HELP button
        dTFileBrowserPage.getHelp().click();
        Utils.waitUntilIsVisible(driver, dTFileBrowserPage.getTooltip());
        Assert.assertTrue(dTFileBrowserPage.getTooltip().isDisplayed());
        
        //Assert the save button is disabled
        Assert.assertFalse("Save Button is enabled but Fields are empty",
                dTFileBrowserCreateTextFilePage.getSaveButton().isEnabled());
        
        //Verify "field required" warning
        dTFileBrowserCreateTextFilePage.getFileName().click();
        dTFileBrowserCreateTextFilePage.getFileContent().click();
        dTFileBrowserCreateTextFilePage.getPageTitle().click();
        Assert.assertTrue("File Name Field Error is not displayed", 
                dTFileBrowserCreateTextFilePage.getFileNameError().isDisplayed());
        Assert.assertTrue("File Content Field Error is not displayed", 
                dTFileBrowserCreateTextFilePage.getFileContentError().isDisplayed());
        
        //Set the fields
        dTFileBrowserCreateTextFilePage.setFileName(createFileName);
        dTFileBrowserCreateTextFilePage.setFileContent(contentFile);
        dTFileBrowserCreateTextFilePage.getSelectType().selectByVisibleText("txt");
        
        //Assert the save button is enabled
        Assert.assertTrue("Save Button is disabled but Folder Name field is compiled",
                dTFileBrowserCreateTextFilePage.getSaveButton().isEnabled());
        
        //Save the data
        dTFileBrowserCreateTextFilePage.getSaveButton().click();
        
        //Wait loading prev. page        
        Utils.waitUntilIsVisible(driver, dTFileBrowserPage.getAlertMessage());
        
        Assert.assertTrue("Alert Message has not displayed",
                dTFileBrowserPage.getAlertMessage().isDisplayed());
        Assert.assertTrue("Invalid Alert Message content. Expected contains \"...complete\"",
                dTFileBrowserPage.getAlertMessageContent().contains("complete"));
        dTFileBrowserPage.getCloseMessageButton().click();
        
        sleep(400);
        Utils.waitUntilIsVisible(driver, dTFileBrowserPage.getTableBody());
        
        //Assert the presence of the created file in the file browser table
        List<WebElement> createdFolder = dTFileBrowserPage.getTable()
                .findRowList(createFileName + ".txt", 0);
        Assert.assertFalse("Created file is not present in the file browser table",
                createdFolder.isEmpty());
        
        //Delete the file
        Assert.assertTrue("File has not been deleted",
                deleteFile(dTFileBrowserPage, createFileName + ".txt"));
        
        /** Debug code **/
        if(Logger.getGlobal().getLevel() == Level.INFO){
            sleep(SLEEPTIME);
        }
        /** End Debug code**/ 
        
    }
}
