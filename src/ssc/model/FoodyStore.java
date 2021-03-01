/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.model;

import Setting.ToolSetting;
import Utils.StringUtil;
import java.sql.ResultSet;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ssc.automation.selennium.SeleniumJSUtils;
import ssc.automation.selennium.constants;
import ssc.facking.ProrxyRunning;
import ssc.proxy.ProxyInfo;
import task.EventFinistTask;

/**
 *
 * @author PC
 */
public class FoodyStore extends Task<Boolean> {

    private int stt;
    private String id;
    private SimpleStringProperty address;
    private String email;
    private String link;
    private SimpleStringProperty sdt;
    private String website;
    private String fanpage;
    private String name;
    private ProxyInfo proxy;

    public ProxyInfo getProxy() {
        return proxy;
    }

    public void setProxy(ProxyInfo proxy) {
        this.proxy = proxy;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public SimpleStringProperty sdtProperty() {
        return sdt;
    }

    public String getSdt() {
        return sdt.get();
    }

    public void setSdt(String sdt) {
        this.sdt.set(sdt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFanpage() {
        return fanpage;
    }

    public void setFanpage(String fanpage) {
        this.fanpage = fanpage;
    }
    private FoodyStoreFunction func;

    public FoodyStoreFunction getFunc() {
        if (func == null) {
            func = new FoodyStoreFunction(this);
        }
        return func;
    }

    public String insert() {
        return getFunc().insertData();
    }

    public String update() {
        return getFunc().updateData();
    }

    public String delete() {
        return getFunc().deleteData();
    }

    public void eventListener(EventFinistTask myEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        myEvent.main();
                    }
                });
                setOnFailed(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        myEvent.main();
                    }
                });
                setOnCancelled(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        myEvent.main();
                    }
                });
            }
        });
    }

    public FoodyStore() {
        this.sdt = new SimpleStringProperty("n/a");
        this.address = new SimpleStringProperty("n/a");
    }

    public FoodyStore(ResultSet rs) {
        this.id = StringUtil.getStringFromRS("id", rs);
        this.address = new SimpleStringProperty(StringUtil.getStringFromRS("address", rs));
        this.email = StringUtil.getStringFromRS("email", rs);
        this.link = StringUtil.getStringFromRS("link", rs);
        this.sdt = new SimpleStringProperty(StringUtil.getStringFromRS("sdt", rs));
        this.website = StringUtil.getStringFromRS("website", rs);
        this.fanpage = StringUtil.getStringFromRS("fanpage", rs);
        this.name = StringUtil.getStringFromRS("name", rs);
    }
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
    private WebDriverRunning webDriverRunning;

    public WebDriverRunning getWebDriverRunning() {
        return webDriverRunning;
    }

    public void setWebDriverRunning(WebDriverRunning webDriverRunning) {
        this.webDriverRunning = webDriverRunning;
    }

    @Override
    protected Boolean call() {
        ProrxyRunning.getInstance().increaseRunning();
        try {
            sdt.set("Đang lấy");
            address.set("Đang lấy");
            if (webDriverRunning.getDriver() == null) {
                sdt.set("Khởi tạo driver");
                address.set("Khởi tạo driver");;
                driver = constants.getFireFoxGECKOHeadLess(ToolSetting.getInstance().isHideBrowser(), ToolSetting.getInstance().getProfilePath(), proxy);
                webDriverRunning.setDriver(driver);
            } else {
                driver = webDriverRunning.getDriver();
            }
            sdt.set("Đang load info");
            address.set("Đang load info");
            updateMessage(link);
            driver.get(link);
            SeleniumJSUtils.waitForPageLoad(driver, 30);
            if (driver.getCurrentUrl().toLowerCase().contains("thuong-hieu")) {
                String headUrl = driver.findElement(By.xpath("//div[contains(@class,'ldc-item-img')]//a")).getAttribute("href");
                driver.get(headUrl);
                SeleniumJSUtils.waitForPageLoad(driver, 30);
            }
            String address = SeleniumJSUtils.getContentText("//div[contains(@class,'dt-map-txt')]", driver, 0);
            System.out.println("address "+address);
            SeleniumJSUtils.clickElement("//a[contains(@data-target,'modal-lienhe')]", driver);
            Thread.sleep(1000);
            SeleniumJSUtils.waitForPageLoad(driver, 30);
            List<WebElement> ap = driver.findElements(By.xpath("//a[contains(@href,'unsafe:tel:')]//h2"));
            String phone = "";
            for (WebElement webElement : ap) {
                phone = phone + " " + webElement.getText();
            }
            setSdt(phone);
            setAddress(address);
        } catch (Exception e) {
            e.printStackTrace();
            sdt.set("Lỗi");
            address.set("Lỗi");
        } finally {
            webDriverRunning.setRunning(false);
            ProrxyRunning.getInstance().countDownRunning();
            ProrxyRunning.getInstance().increaseRun();
        }
        return true;
    }

    public void start() {
        Thread tg = new Thread(this);
        tg.setDaemon(true);
        tg.start();
    }

}
