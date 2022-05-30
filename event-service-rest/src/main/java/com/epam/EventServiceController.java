package com.epam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class EventServiceController {
        private final EventServiceImpl eventService;

        @Operation(summary = "Get all events")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Find events",
                        content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = List.class)) })})
        @GetMapping("/")
        public ResponseEntity<List<Event>> getEvents(){
                List<Event> allEvents = eventService.getAllEvents();
                allEvents.forEach(event -> event
                        .add(linkTo(methodOn(EventServiceController.class).eventById(event.getId())).withSelfRel())
                                .add(linkTo(methodOn(EventServiceController.class)
                                        .addEvent(allEvents.stream().findAny().get())).withSelfRel()));
                return ResponseEntity.ok(allEvents);
        }

        @Operation(summary = "Get events")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Get events by title contains",
                        content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Event.class)) })})
        @GetMapping("/eventsByTitle")
        public ResponseEntity<List<Event>> getEventsByTitle(@RequestParam(name = "title")String title){

                List<Event> allEventsByTitle = eventService.getAllEventsByTitle(title);
                allEventsByTitle.forEach(event ->
                        event.add(linkTo(methodOn(EventServiceController.class).eventById(event.getId())).withSelfRel()));
                return ResponseEntity.ok(allEventsByTitle);
        }

        @Operation(summary = "Get event")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Get event by id",
                        content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Event.class)) })})
        @GetMapping("/event/{id}")
        public HttpEntity<Event> eventById(@PathVariable(name = "id") long id){
                return ResponseEntity.ok(eventService.getEvent(id));
        }

        @Operation(summary = "Add event")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Add event",
                        content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Event.class)) })})
        @PostMapping("/event")
        public ResponseEntity<Event> addEvent(@RequestBody Event event){
                Event event1 = eventService.createEvent(event);
                event1.add(linkTo(methodOn(EventServiceController.class).eventById(event.getId())).withSelfRel());
                return ResponseEntity.status(HttpStatus.CREATED).body(event1);
        }

        @Operation(summary = "Update event")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Update event",
                        content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Event.class)) })})
        @PutMapping("/event/{id}")
        public ResponseEntity<Event> updateEvent(@PathVariable(name = "id") long id,
                                                 @RequestBody Event event){
                Event event1 = eventService.getEvent(id);
                event1.setEventType(event.getEventType());
                event1.setDateTime(event.getDateTime());
                event1.setSpeaker(event.getSpeaker());
                event1.setTitle(event.getTitle());
                eventService.updateEvent(event1);
                event1.add(linkTo(methodOn(EventServiceController.class).eventById(event.getId())).withSelfRel());
                return ResponseEntity.status(HttpStatus.OK).body(event1);
        }

        @Operation(summary = "Delete event")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Delete event by id",
                        content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Event.class)) })})
        @DeleteMapping("/event/{id}")
        public HttpEntity<Event> deleteEvent(@PathVariable(name = "id") long id){
                eventService.deleteEvent(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
}
