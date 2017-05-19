package defencer.service;

import defencer.model.Apprentice;
import defencer.model.Instructor;
import defencer.model.Project;

import java.util.List;

/**
 * @author Igor Hnes on 19.05.17.
 */
public interface ExcelService {

    /**
     * Preparation excel report for user by given project list.
     */
    void projectReport(List<Project> projects);

    /**
     * Preparation excel report for user by given instructor list.
     */
    void instructorReport(List<Instructor> instructors);

    /**
     * Preparation excel report for user by given apprentice list.
     */
    void apprenticeReport(List<Apprentice> apprentices);
}
