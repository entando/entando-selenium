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
package org.entando.selenium.pages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.entando.selenium.utils.*;
import org.openqa.selenium.*;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DTLoginPage extends PageObject {

    @FindBy(id = "username")
    private WebElement userName;
    @FindBy(id = "password")
    private WebElement passWord;

    @FindBy(xpath = "//button[contains(@*, 'submit')]")
    private WebElement submitButton;

    public void logIn(String userName, String passWord) {
        this.userName.clear();
        this.userName.sendKeys(userName);

        this.passWord.clear();
        this.passWord.sendKeys(passWord);
        
        this.submitButton.click();
    }

    public DTLoginPage(WebDriver driver) {
        super(driver);
        //driver.get("http://localhost:3000");
        driver.get("http://appbuilder.serv.run");
        
	
        
        
        
        
        
        
        
    }
   

}
