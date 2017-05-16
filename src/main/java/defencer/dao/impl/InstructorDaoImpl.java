package defencer.dao.impl;

import defencer.dao.InstructorDao;
import defencer.model.Expected;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.model.Schedule;
import defencer.model.enums.Role;
import defencer.util.HibernateUtil;
import lombok.val;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Comparator;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implementation of {@link InstructorDao} interface.
 *
 * @author Igor Gnes on 3/30/17.
 */
public class InstructorDaoImpl extends CrudDaoImpl<Instructor> implements InstructorDao {

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor findByEmail(String email) {
        final Session session = getSession();
        session.beginTransaction();
        Instructor instructor;
        final Query emailQuery = session.createQuery("from Instructor where email = :email").setParameter("email", email);
        try {
            instructor = (Instructor) emailQuery.getSingleResult();
        } catch (NoResultException e) {
            session.getTransaction().commit();
            session.close();
            return null;
        }
        session.getTransaction().commit();
        session.close();
        return instructor;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor findByPhone(String phone) {
        final Session session = getSession();
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Project findProjectByInstructor(Long id) {
        final Session session = getSession();
        session.beginTransaction();

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Project> projectCriteriaQuery = criteriaBuilder.createQuery(Project.class);
        final Root<Project> root = projectCriteriaQuery.from(Project.class);
        projectCriteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        final Project singleResult;
        try {
            singleResult = session.createQuery(projectCriteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            session.close();
            return null;
        }

        session.getTransaction().commit();
        session.close();
        return singleResult;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> getInstructors() {
        final Session session = getSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Instructor> criteriaQuery = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> root = criteriaQuery.from(Instructor.class);
        criteriaQuery.select(root);
        final List<Instructor> instructorList = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        instructorList.sort(Comparator.comparing(Instructor::getRole));
        return instructorList;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> findAdmins() {
        final Session session = getSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Instructor> criteriaBuilderQuery = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> root = criteriaBuilderQuery.from(Instructor.class);
        criteriaBuilderQuery.select(root)
                .where(criteriaBuilder.equal(root.get("role"), Role.CHIEF_OFFICER));
        final List<Instructor> admins = session.createQuery(criteriaBuilderQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return admins;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Schedule> getMyProject(Long userId) {
        Session session = getSession();
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val expectedCriteriaQuery = criteriaBuilder.createQuery(Expected.class);
        Root<Expected> root = expectedCriteriaQuery.from(Expected.class);
        expectedCriteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("instructorId"), userId));
        List<Expected> myProject = session.createQuery(expectedCriteriaQuery).getResultList();

        val query = criteriaBuilder.createQuery(Schedule.class);
        final Root<Schedule> toor = query.from(Schedule.class);
        query.select(toor)
                .where(criteriaBuilder.equal(root.get("instructorId"), userId));
        final List<Schedule> schedules = session.createQuery(query).getResultList();
        session.getTransaction().commit();
        session.close();
        myProject.forEach(s -> {
            final Schedule schedule = new Schedule();
            schedule.setProjectId(s.getProjectId());
            schedule.setProjectName(s.getProjectName());
            schedule.setStartProject(s.getProjectStart());
            schedule.setFinishProject(s.getFinishProject());
            schedule.setInstructorId(s.getInstructorId());
            schedule.setInstructorName(s.getInstructorNames());
            schedule.setStatus(s.getStatus());
            schedules.add(schedule);
        });
        schedules.sort(Comparator.comparing(Schedule::getStartProject));
        return schedules;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void changePassword(Long userId, String password) {
        final Session session = getSession();
        session.beginTransaction();

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Instructor.class);
        final Root<Instructor> root = criteriaUpdate.from(Instructor.class);
        criteriaUpdate.set(root.get("password"), password)
                .where(criteriaBuilder.equal(root.get("id"), userId));
        session.createQuery(criteriaUpdate).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    /**
     * @return {@link Session} for next steps.
     */
    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
