package defencer.data;

import defencer.model.AbstractEntity;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.factory.ServiceFactory;
import lombok.Getter;

import java.sql.SQLException;
import java.time.LocalDate;
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
        data.put("status", currentUser.getStatus());
        data.put("role", currentUser.getRole());
    }


    /**
     * Save user at database.
     */
    public void save() {

        busy = true;

        final Instructor instructor = ServiceFactory.getInstructorService().findByEmail(CurrentUser.getLink().getEmail());
        instructor.setFirstName((String) data.get("firstName"));
        instructor.setLastName((String) data.get("lastName"));
        instructor.setEmail((String) data.get("email"));
        instructor.setPhone((String) data.get("phoneNumber"));

        try {
            ServiceFactory.getInstructorService().updateEntity(instructor);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        busy = false;
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

    public CurrentUser withFirstName(String firstName) {
        data.put("firstName", firstName);
        return this;
    }

    public String getLastName() {
        return (String) data.get("lastName");
    }

    public CurrentUser withLastName(String lastName) {
        data.put("lastName", lastName);
        return this;
    }

    public String getPhoneNumber() {
        return (String) data.get("phoneNumber");
    }

    public CurrentUser withPhoneNumber(String phoneNumber) {

        data.put("phoneNumber", phoneNumber);
        return this;
    }

    public String getEmail() {
        return (String) data.get("email");
    }

    public CurrentUser withEmail(String email) {

        data.put("email", email);
        return this;
    }

    public String getStatus() {
        return (String) data.get("status");
    }

    public String hasRole() {
        return (String) data.get("role");
    }

    public Long getProjectId() {
        return (Long) data.get("projectId");
    }

    public String getProjectNameId() {
        return (String) data.get("getNameId");
    }

    public LocalDate getProjectDateStart() {
        return (LocalDate) data.get("dateStart");
    }

    public LocalDate getProjectDateFinish() {
        return (LocalDate) data.get("dateFinish");
    }

    public String getProjectPlace() {
        return (String) data.get("place");
    }

    public String getProjectAuthor() {
        return (String) data.get("author");
    }

    public String getProjectDescription() {
        return (String) data.get("description");
    }
}
