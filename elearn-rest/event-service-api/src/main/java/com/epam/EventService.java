package com.epam;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {

    Event createEvent(Event event);
    Event updateEvent(Event event);
    void deleteEvent(long id);
    Event getEvent(long id);
    List<Event> getAllEvents();
    List<Event> getAllEventsByTitle(String title);
}
