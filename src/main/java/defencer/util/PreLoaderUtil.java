package defencer.util;

import javafx.concurrent.Task;
import lombok.Getter;
import org.hibernate.SessionFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * @author Nikita on 19.04.2017.
 */
public class PreLoaderUtil extends Thread {

    private static PreLoaderUtil preLoaderUtil;
    @Getter
    private boolean isPreLoaded = false;
    @Getter
    private static Double percents = 0.0;
    private List<Task<Void>> tasks = new LinkedList<>();

    private PreLoaderUtil() {
        Task<Void> testTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                if(InternetConnectionCheckerUtil.checkConnection()) {
                    percents = 1.0 / 2;
                    final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                    sessionFactory.openSession();
                }
                percents = 1.0;
                return null;
            }
        };
        tasks.add(testTask);
    }

    @Override
    public void run() {
        tasks.forEach(FutureTask::run);
        isPreLoaded = true;
    }

    /**
     * Getting link.
     */
    public static PreLoaderUtil getLink() {
        if (preLoaderUtil == null) {
            preLoaderUtil = new PreLoaderUtil();
        }
        return preLoaderUtil;
    }


    public static PreLoaderUtil restart() {
        preLoaderUtil = null;

        return getLink();
    }
}
