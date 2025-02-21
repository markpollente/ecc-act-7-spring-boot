package com.markp.app.controller;

import com.markp.dto.HelpdeskTicketDto;
import com.markp.service.HelpdeskTicketService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class HelpdeskTicketController {

    @Autowired
    private HelpdeskTicketService ticketService;

    @PostMapping
    public ResponseEntity<HelpdeskTicketDto> createTicket(@RequestBody HelpdeskTicketDto ticketDto) {
        HelpdeskTicketDto savedTicket = ticketService.createTicket(ticketDto);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<HelpdeskTicketDto> getTicketById(@PathVariable("id") Long ticketId) {
        HelpdeskTicketDto ticketDto = ticketService.getTicketById(ticketId);
        return ResponseEntity.ok(ticketDto);
    }

    @GetMapping
    public ResponseEntity<List<HelpdeskTicketDto>> getAllTickets() {
        List<HelpdeskTicketDto> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<HelpdeskTicketDto>> getTicketsByStatus(@PathVariable("status") String status) {
        List<HelpdeskTicketDto> tickets = ticketService.getTicketsByStatus(status);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<HelpdeskTicketDto>> getTicketsByAssignee(@PathVariable("assigneeId") Long assigneeId) {
        List<HelpdeskTicketDto> tickets = ticketService.getTicketsByAssignee(assigneeId);
        return ResponseEntity.ok(tickets);
    }

    @PutMapping("{id}")
    public ResponseEntity<HelpdeskTicketDto> updateTicket(@PathVariable("id") Long ticketId,
                                                          @RequestBody HelpdeskTicketDto updatedTicket) {
        HelpdeskTicketDto ticketDto = ticketService.updateTicket(ticketId, updatedTicket);
        return ResponseEntity.ok(ticketDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable("id") Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return ResponseEntity.ok("Ticket deleted successfully.");
    }

    @PutMapping("{ticketId}/assign/{employeeId}")
    public ResponseEntity<HelpdeskTicketDto> assignTicketToEmployee(@PathVariable("ticketId") Long ticketId,
                                                                    @PathVariable("employeeId") Long employeeId) {
        HelpdeskTicketDto ticketDto = ticketService.assignTicketToEmployee(ticketId, employeeId);
        return ResponseEntity.ok(ticketDto);
    }

    @PutMapping("{ticketId}/remark")
    public ResponseEntity<HelpdeskTicketDto> addRemarkAndUpdateStatus(@PathVariable("ticketId") Long ticketId,
                                                                      @RequestParam("remarks") String remarks,
                                                                      @RequestParam("status") String status) {
        HelpdeskTicketDto ticketDto = ticketService.addRemarkAndUpdateStatus(ticketId, remarks, status);
        return ResponseEntity.ok(ticketDto);
    }
}
