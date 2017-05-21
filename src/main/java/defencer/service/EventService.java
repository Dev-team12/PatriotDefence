package defencer.service;

import defencer.model.Event;

/**
 * @author Nikita on 05.05.2017.
 */
public interface EventService extends CrudService<Event, Long> {

    /**
     * Add new event to calendar.
     */
    void addEvent(Event event);
}
