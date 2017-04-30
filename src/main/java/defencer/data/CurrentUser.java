package defencer.data;

import defencer.model.AbstractEntity;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.factory.ServiceFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikita on 16.04.2017.
 */
public class CurrentUser extends AbstractEntity {

    private static CurrentUser currentUser;

    private Map<String, Object> data;

    private CurrentUser() {
        data = new HashMap<>();
//        data.put("id", id);
    }

    /**
     * Creating new instance of user.
     */
    public static CurrentUser newInstance() {
//        if (currentUser != null) {
//            currentUser.data = null;
//        }
        currentUser = new CurrentUser();
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
     * Downloading data for user.
     */
    private void downloadData() {
        final Instructor currentUser = ServiceFactory.getWiseacreService().getCurrentUser("joyukr@ukr.net");
        Project projectByInstructor = ServiceFactory.getInstructorService().findProjectByInstructor(currentUser.getProjectId());
        if (projectByInstructor == null) {
            projectByInstructor = new Project();
            projectByInstructor.setName("none");
            projectByInstructor.setDateStart(LocalDate.MIN);
            projectByInstructor.setDateFinish(LocalDate.MAX);
            projectByInstructor.setPlace("-");
            projectByInstructor.setAuthor("-");
            projectByInstructor.setDescription("-");

        }
        data.put("id", currentUser.getId());
        data.put("firstName", currentUser.getFirstName());
        data.put("lastName", currentUser.getLastName());
        data.put("phoneNumber", currentUser.getPhone());
        data.put("email", currentUser.getEmail());
        data.put("status", currentUser.getStatus());
        data.put("role", currentUser.getRole());

        data.put("projectName", projectByInstructor.getName());
        data.put("dateStart", projectByInstructor.getDateStart());
        data.put("dateFinish", projectByInstructor.getDateFinish());
        data.put("place", projectByInstructor.getPlace());
        data.put("author", projectByInstructor.getAuthor());
        data.put("description", projectByInstructor.getDescription());
    }

    public static void refresh() {
        currentUser = null;
        newInstance();
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

    public String getStatus() {
        return (String) data.get("status");
    }

    public String hasRole() {
        return (String) data.get("role");
    }

    public String getProjectName() {
        return (String) data.get("projectName");
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
