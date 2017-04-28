package defencer.data;

import defencer.model.Instructor;
import defencer.service.factory.ServiceFactory;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikita on 16.04.2017.
 */
@NoArgsConstructor
public class CurrentUser {

    private static CurrentUser currentUser = null;

    private Map<String, Object> data = null;

    private CurrentUser(Long id) {
        data = new HashMap<>();
        data.put("id", id);
    }

    /**
     * Creating new instance of user.
     */
    public static CurrentUser newInstance(long id) {
        if (currentUser != null) {
            currentUser.data = null;
        }
        currentUser = new CurrentUser(id);
        currentUser.downloadData();

        return currentUser;
    }

    /**
     * Getting link of user.
     */
    public static CurrentUser getLink() {
        return currentUser;
    }

    /**
     * Downloading data 4 user.
     */
    private void downloadData() {
        final Instructor currentUser = ServiceFactory.getWiseacreService().getCurrentUser("joyukr@ukr.net");
//
//
//        HibernateQueryBuilder hibernateQueryBuilder = new HibernateQueryBuilder(HibernateQueryBuilder.SELECT_QUERY, Instructor.class);
//        hibernateQueryBuilder.with(HibernateQueryBuilder.ID_FIELD, data.get("id"));
//
//        Instructor userData = (Instructor) HibernateService.executeQuery(hibernateQueryBuilder).get(0);

        data.put("firstName", currentUser.getFirstName());
        data.put("lastName", currentUser.getLastName());
        data.put("phoneNumber", currentUser.getPhone());
        data.put("email", currentUser.getEmail());
        data.put("status", currentUser.getStatus());
        data.put("role", currentUser.getRole());
    }

    public String getId() {
        return (String) data.get("name");
    }

    public String getFirstName() {
        return (String) data.get("firstName");
    }
    /*public CurrentUser withFirstName(String name) {
        data.put("name", name);
        return currentUser;
    }*/
    
    public String getLastName() {
        return (String) data.get("lastName");
    }
    /*public CurrentUser withLastName(String name) {
        data.put("name", name);
        return currentUser;
    }*/

    public String getPhoneNumber() {
        return (String) data.get("phoneNumber");
    }
    /*public CurrentUser withPhoneNumber(String phoneNumber) {
        data.put("phoneNumber", phoneNumber);
        return currentUser;
    }*/

    public String getEmail() {
        return (String) data.get("email");
    }
   /* public CurrentUser withEmai(String email) {
        data.put("email", email);
        return currentUser;
    }*/

    public String getStatus() {
        return (String) data.get("status");
    }
    /*public CurrentUser withStatus(Boolean status) {
        data.put("status", status);
        return currentUser;
    }*/

    public String hasRole() {
        return (String) data.get("role");
    }
}
