package com.markp.service;

import com.markp.dto.HelpdeskTicketDto;
import com.markp.exception.ResourceNotFoundException;
import com.markp.mapper.HelpdeskTicketMapper;
import com.markp.model.Employee;
import com.markp.model.HelpdeskTicket;
import com.markp.repository.EmployeeRepository;
import com.markp.repository.HelpdeskTicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HelpdeskTicketServiceImpl implements HelpdeskTicketService {

    private HelpdeskTicketRepository ticketRepository;
    private EmployeeRepository employeeRepository;

    @Override
    public HelpdeskTicketDto createTicket(HelpdeskTicketDto ticketDto) {
        HelpdeskTicket ticket = HelpdeskTicketMapper.mapToHelpdeskTicket(ticketDto);
        ticket.setCreatedDate(LocalDateTime.now());
        ticket.setTicketNo(generateTicketNo());
        HelpdeskTicket savedTicket = ticketRepository.save(ticket);
        return HelpdeskTicketMapper.mapToHelpdeskTicketDto(savedTicket);
    }

    @Override
    public HelpdeskTicketDto getTicketById(Long ticketId) {
        HelpdeskTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ticket does not exist with given id: " + ticketId));

        return HelpdeskTicketMapper.mapToHelpdeskTicketDto(ticket);
    }

    @Override
    public List<HelpdeskTicketDto> getAllTickets() {
        List<HelpdeskTicket> tickets = ticketRepository.findAll();
        return tickets.stream().map(HelpdeskTicketMapper::mapToHelpdeskTicketDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HelpdeskTicketDto> getTicketsByAssignee(Long assigneeId) {
        List<HelpdeskTicket> tickets = ticketRepository.findByAssigneeId(assigneeId);
        return tickets.stream().map(HelpdeskTicketMapper::mapToHelpdeskTicketDto)
                .collect(Collectors.toList());
    }

    @Override
    public HelpdeskTicketDto updateTicket(Long ticketId, HelpdeskTicketDto updatedTicket) {
        HelpdeskTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ticket does not exist with given id: " + ticketId));

        ticket.setTitle(updatedTicket.getTitle());
        ticket.setBody(updatedTicket.getBody());
        ticket.setStatus(updatedTicket.getStatus());
        ticket.setUpdatedDate(LocalDateTime.now());
        ticket.setUpdatedBy(updatedTicket.getUpdatedBy());
        ticket.setRemarks(updatedTicket.getRemarks());

        HelpdeskTicket updatedTicketObj = ticketRepository.save(ticket);
        return HelpdeskTicketMapper.mapToHelpdeskTicketDto(updatedTicketObj);
    }

    @Override
    public void deleteTicket(Long ticketId) {
        HelpdeskTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ticket does not exist with given id: " + ticketId));

        ticketRepository.deleteById(ticketId);
    }

    @Override
    public HelpdeskTicketDto assignTicketToEmployee(Long ticketId, Long employeeId) {
        HelpdeskTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ticket does not exist with given id: " + ticketId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee does not exist with given id: " + employeeId));
        ticket.setAssignee(employee);
        HelpdeskTicket updatedTicket = ticketRepository.save(ticket);
        return HelpdeskTicketMapper.mapToHelpdeskTicketDto(updatedTicket);
    }

    @Override
    public HelpdeskTicketDto addRemarkAndUpdateStatus(Long ticketId, String remarks, String status) {
        HelpdeskTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Ticket does not exist with given id: " + ticketId));
        ticket.setRemarks(remarks);
        ticket.setStatus(status);
        ticket.setUpdatedDate(LocalDateTime.now());
        HelpdeskTicket updatedTicket = ticketRepository.save(ticket);
        return HelpdeskTicketMapper.mapToHelpdeskTicketDto(updatedTicket);
    }

    private String generateTicketNo() {
        Random random = new Random();
        int number = random.nextInt(99999);
        return "Ticket #" + String.format("%06d", number);
    }
}