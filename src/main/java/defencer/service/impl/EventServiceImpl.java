package defencer.service.impl;

import defencer.model.Event;
import defencer.service.EventService;

import java.sql.SQLException;

/**
 * @author Nikita on 05.05.2017.
 */
public class EventServiceImpl extends CrudServiceImpl<Event> implements EventService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public void addEvent(Event event) {
        try {
            super.createEntity(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
