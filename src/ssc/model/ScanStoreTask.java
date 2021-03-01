/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.model;

import static MainView.SubView.MakeMusicVideoView.City;
import Setting.ToolSetting;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ssc.automation.selennium.SeleniumJSUtils;
import ssc.automation.selennium.constants;
import ssc.facking.ProrxyRunning;
import ssc.proxy.ProxyInfo;
/**
 * @author PC
 */
public class ScanStoreTask extends Task<Boolean> {

    private String city;
    private String category;
    private int fromPage;
    private int numberScan;
    private ObservableList<FoodyStore> data;
    private String q;
    public ScanStoreTask(int fromPage,String city, String category, int numberScan, ObservableList<FoodyStore> data,String q) {
        this.city = city;
        this.category = category;
        this.numberScan = numberScan;
        this.data = data;
        this.q=q;
        this.fromPage=fromPage;
    }
    private int countLoad = 0;

    @Override
    protected Boolean call() {
        WebDriver driver = null;
        try {
            int page = fromPage;
            ProxyInfo proxy = ProrxyRunning.getInstance().getAvailableSanStoreProxy();
            driver = constants.getFireFoxGECKOHeadLess(ToolSetting.getInstance().isHideBrowser(), proxy);
            while (true) {
                try {
                    if (countLoad >= 500){
                        driver.quit();
                        updateMessage("Đang đổi proxy");
                        proxy = ProrxyRunning.getInstance().getAvailableSanStoreProxy();
                        driver = constants.getFireFoxGECKOHeadLess(ToolSetting.getInstance().isHideBrowser(), proxy);
                        countLoad = 0;
                    }
                    updateMessage("["+countLoad+"/500]Đang load trang " + page);
                    //https://www.foody.vn/ho-chi-minh/dia-diem?ds=Restaurant&vt=row&st=1&q=cafe&page=4&provinceId=217&categoryId=&append=true
                    driver.get("https://www.foody.vn/ho-chi-minh/" + category + "/dia-diem?ds=Restaurant&vt=row&st=1&q="+q+"&page=" + page + "&provinceId=" + (City.indexOf(city) + 217) + "&categoryId=&append=true");
                    SeleniumJSUtils.waitForPageLoad(driver, 30);
                    countLoad++;
                    int length = SeleniumJSUtils.getLengthJS("//div[contains(@id,'result-box')]//div[contains(@class,'row-item')]", driver);
                    if (length == 0) {
                        break;
                    }
                    updateMessage("["+countLoad+"/500]Đang load trang " + page + " " + length + " cửa hàng");
                    List<WebElement> arrStore = driver.findElements(By.xpath("//div[contains(@id,'result-box')]//div[contains(@class,'row-item')]"));
                    List<String> arrHead = new ArrayList<>();
                    for (WebElement head : arrStore) {
                        if (head.findElement(By.tagName("a")).getAttribute("href").contains("thuong-hieu")) {
                            arrHead.add(head.findElement(By.tagName("a")).getAttribute("href"));
                        }
                    }
                    for (int i = 0; i < length; i++) {
                        try {
                            WebElement store = arrStore.get(i);
                            if (!store.findElement(By.tagName("a")).getAttribute("href").contains("thuong-hieu")) {
                                String name = store.findElement(By.tagName("a")).getAttribute("title");
                                FoodyStore fs = new FoodyStore();
                                fs.setName(name);
                                fs.setStt(data.size() + 1);
                                fs.setLink(store.findElement(By.tagName("a")).getAttribute("href"));
                                data.add(fs);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    for (String string : arrHead) {
                        try {
                            driver.get(string);
                            SeleniumJSUtils.waitForPageLoad(driver, 30);
                            updateMessage("["+countLoad+"/500]Đang load đại lý cửa hàng");
                            countLoad++;
                           
                            List<WebElement> arrHeadStore = driver.findElements(By.xpath("//div[contains(@class,'ldc-item-img')]"));
                            for (WebElement head : arrHeadStore) {
                                try {
                                    String name = head.findElement(By.tagName("a")).getAttribute("title");
                                    FoodyStore fs = new FoodyStore();
                                    fs.setName(name);
                                    fs.setStt(data.size() + 1);
                                    fs.setLink(head.findElement(By.tagName("a")).getAttribute("href"));
                                    data.add(fs);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    
                    page++;
                    System.out.println(city+"-"+category+"-"+q.toLowerCase());
                    ToolSetting.getInstance().getPre().putInt(city+"-"+category+"-"+q.toLowerCase(), page);
                } catch (Exception e) {

                } finally {
                   // driver.quit();
                }
            }
        } catch (Exception e) {
        } finally {
            driver.quit();
        }

        return true;
    }

    public void start() {
        Thread tg = new Thread(this);
        tg.setDaemon(true);
        tg.start();
    }

}
