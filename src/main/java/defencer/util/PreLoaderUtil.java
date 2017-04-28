package defencer.util;

import defencer.data.CurrentUser;
import javafx.concurrent.Task;
import lombok.Getter;

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

    private final Long tempId = 30L;


    private PreLoaderUtil() {
        Task<Void> testTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
//                HibernateUtil.getSessionFactory().getCurrentSession();
                percents = 1.0 / 2;
                System.out.println("1");
                CurrentUser.newInstance(tempId);
                percents = 1.0;
                System.out.println("2");
                return null;
            }
        };
        tasks.add(testTask);
    }


    @Override
    public void run() {
//        for (Task<Void> task : tasks) {
//            task.run();
//        }
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
}
