package com.markp.repository;

import com.markp.model.HelpdeskTicket;
import com.markp.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelpdeskTicketRepository extends JpaRepository<HelpdeskTicket, Long> {
    List<HelpdeskTicket> findByStatus(TicketStatus status);
    List<HelpdeskTicket> findByAssigneeId(Long assigneeId);
}