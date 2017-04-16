package defencer.data;

import defencer.model.Apprentice;
import defencer.temp.HibernateService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikita on 16.04.2017.
 */
public class CurrentUser {

    private static CurrentUser currentUser = null;

    private Map<String,Object> data = null;



    private CurrentUser(String id){
        data = new HashMap<>();
        data.put("id",id);

        ControllersDataFactory.getLink().update(this.getClass(),data);
        downloadData();
    }



    public static CurrentUser newInstance(String id){

        if(currentUser != null){
            currentUser.data = null;
        }

        currentUser = new CurrentUser(id);
        return currentUser;
    }



    public static CurrentUser getLink(){
        return currentUser;
    }



    private void downloadData(){
        Apprentice userData = HibernateService.getApprenticeData((String)data.get("id"));

        data.put("name",userData.getName());
        data.put("phoneNumber",userData.getPhone());
        data.put("email",userData.getEmail());
        data.put("status",true);
    }



    public String getId(){
        return (String) data.get("name");
    }

    public String getName(){
        return (String) data.get("name");
    }
    public CurrentUser withName(String name){
        data.put("name",name);
        return currentUser;
    }

    public String getPhoneNumber(){
        return (String) data.get("phoneNumber");
    }
    public CurrentUser withPhoneNumber(String phoneNumber){
        data.put("phoneNumber",phoneNumber);
        return currentUser;
    }

    public String getEmail(){
        return (String) data.get("email");
    }
    public CurrentUser withEmai(String email){
        data.put("email",email);
        return currentUser;
    }

    public Boolean getStatus(){
        return (Boolean) data.get("status");
    }
    public CurrentUser withStatus(Boolean status){
        data.put("status",status);
        return currentUser;
    }
}
