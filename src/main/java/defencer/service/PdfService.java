package defencer.service;

import defencer.model.Apprentice;
import defencer.model.Instructor;
import defencer.model.Project;

import java.util.List;

/**
 * @author Igor Gnes on 5/4/17.
 */
public interface PdfService {

    /**
     * Preparing pdf report by given project list.
     */
    void projectReport(List<Project> projects);

    /**
     * Preparing pdf report by given instructors list.
     */
    void instructorReport(List<Instructor> instructors);

    /**
     * Preparing pdf report by given apprentice list.
     */
    void apprenticeReport(List<Apprentice> apprentices);
}
