package com.markp.service;

import com.markp.dto.HelpdeskTicketDto;

import java.util.List;

public interface HelpdeskTicketService {
    HelpdeskTicketDto createTicket(HelpdeskTicketDto ticketDto);

    HelpdeskTicketDto getTicketById(Long ticketId);

    List<HelpdeskTicketDto> getAllTickets();

    List<HelpdeskTicketDto> getTicketsByStatus(String status);

    List<HelpdeskTicketDto> getTicketsByAssignee(Long assigneeId);

    HelpdeskTicketDto updateTicket(Long ticketId, HelpdeskTicketDto updatedTicket);

    void deleteTicket(Long ticketId);

    HelpdeskTicketDto assignTicketToEmployee(Long ticketId, Long employeeId);

    HelpdeskTicketDto addRemarkAndUpdateStatus(Long ticketId, String remarks, String status);
}