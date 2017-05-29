package defencer.service;

import defencer.model.Instructor;
import defencer.model.Project;

import java.util.List;

/**
 * @author Igor Hnes on 29.05.17.
 */
public interface TelegramService {

    /**
     * @param instructors selected for project.
     * @param project is future project.
     */
    void send(List<Instructor> instructors, Project project);
}
