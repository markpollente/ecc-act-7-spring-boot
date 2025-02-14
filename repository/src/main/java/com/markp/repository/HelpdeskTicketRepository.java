package com.markp.repository;

import com.markp.model.HelpdeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelpdeskTicketRepository extends JpaRepository<HelpdeskTicket, Long> {
    List<HelpdeskTicket> findByStatus(String status);
    List<HelpdeskTicket> findByAssigneeId(Long assigneeId);
}