/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.model;

import static SQliteUtils.SQLIteHelper.executeQuery;

/**
 *
 * @author PC
 */
public class FoodyStoreFunction {
    private FoodyStore foodyStore;

    public FoodyStoreFunction(FoodyStore foodyStore) {
        this.foodyStore = foodyStore;
    }
    
    public String deleteData() {
        String query = "delete from foody where id = '" + foodyStore.getId()+ "'";
        return executeQuery(query);
    }
    public String updateData() {
        /*
        String query = "update foody set "
                + "account_passwd='" + SQliteUtils.SQLIteHelper.enCodeSQLString(account.getAccountPasswd())+"'"
                + ",account_group='" + account.getAccountGroup()+"'"
                + ",account_email_recover='" + account.getAccountEmailRecover()+"'"
                + ",account_phone_recover='" + account.getAccountPhoneRecover()+"'"
                + ",account_useragent='" + account.getAccountUserAgent()+"'"
                + ",account_local_ip='" + account.getAccountLocalIp()+"'"
                + ",account_logouted=" + account.getAccountLogouted()+""
                + ",account_proxy='" + account.getAccountProxy()+"'"
                + ",account_sync=" + account.getAccountSync()+""
                + ",account_note=" + account.getAccountNote()+""
                + ",account_status=" + account.getAccountStatus()+""
                + ",account_last_time_action="+System.currentTimeMillis()+""
                + ",account_total_time_action=" + account.getAccountTotalTimeAction()+""
                + ",account_last_ip='" + account.getAccountLastIp()+"'"
                + ",account_profile_path='" + account.getAccountProfilePath()+"'"
                + ",account_last_action='" + account.getAccountLastAction()+"'"
                + " where account_id = '" + account.getAccountId()+ "'";  
        return executeQuery(query);
        */
        return "";
    }
    public String insertData() {
        String query = "INSERT INTO foody("
                + "id,address,email"
                + ",link,sdt,website,fanpage,name) "
                + " VALUES("
                + "'"+foodyStore.getId()+ "','" + SQliteUtils.SQLIteHelper.enCodeSQLString(foodyStore.getAddress())+ "','" + foodyStore.getEmail() +"'"
                + ",'"+foodyStore.getLink()+"','"+foodyStore.getSdt()+"','"+foodyStore.getWebsite()+"'"
                + ",'"+SQliteUtils.SQLIteHelper.enCodeSQLString(foodyStore.getName())+"')";
        String insertMessage = executeQuery(query);
        return insertMessage;
    }
}
