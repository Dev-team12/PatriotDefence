package defencer.data;

import defencer.model.AbstractEntity;
import defencer.model.Instructor;
import defencer.service.factory.ServiceFactory;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikita on 16.04.2017.
 */
public class CurrentUser extends AbstractEntity {

    private static CurrentUser currentUser;

    private Map<String, Object> data;
    @Getter
    private boolean busy = false;

    private CurrentUser() {
        data = new HashMap<>();
    }

    /**
     * Creating new instance of user.
     */
    public static CurrentUser newInstance(String email) {
        currentUser = new CurrentUser();
        currentUser.downloadData(email);
        return currentUser;
    }

    /**
     * Getting link of user.
     */
    public static CurrentUser getLink() {
        return currentUser;
    }

    /**
     * Downloading data for user.
     */
    private void downloadData(String email) {
        final Instructor currentUser = ServiceFactory.getWiseacreService().getCurrentUser(email);

        data.put("id", currentUser.getId());
        data.put("firstName", currentUser.getFirstName());
        data.put("lastName", currentUser.getLastName());
        data.put("phoneNumber", currentUser.getPhone());
        data.put("email", currentUser.getEmail());
        data.put("role", currentUser.getRole());
    }

    public static void refresh(String email) {
        currentUser = null;
        newInstance(email);
    }

    public static void out() {
        currentUser = null;
    }

    public Long getId() {
        return (Long) data.get("id");
    }

    public String getFirstName() {
        return (String) data.get("firstName");
    }

    public String getLastName() {
        return (String) data.get("lastName");
    }

    public String getPhoneNumber() {
        return (String) data.get("phoneNumber");
    }

    public String getEmail() {
        return (String) data.get("email");
    }

    public String hasRole() {
        return (String) data.get("role");
    }

    public Long getProjectId() {
        return (Long) data.get("projectId");
    }
}
