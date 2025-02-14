package com.markp.mapper;

import com.markp.dto.HelpdeskTicketDto;
import com.markp.model.HelpdeskTicket;

public class HelpdeskTicketMapper {

    public static HelpdeskTicketDto mapToHelpdeskTicketDto(HelpdeskTicket ticket) {
        return new HelpdeskTicketDto(
                ticket.getId(),
                ticket.getTicketNo(),
                ticket.getTitle(),
                ticket.getBody(),
                EmployeeMapper.mapToEmployeeDto(ticket.getAssignee()),
                ticket.getStatus(),
                ticket.getCreatedDate(),
                ticket.getCreatedBy(),
                ticket.getUpdatedDate(),
                ticket.getUpdatedBy(),
                ticket.getRemarks()
        );
    }

    public static HelpdeskTicket mapToHelpdeskTicket(HelpdeskTicketDto ticketDto) {
        return new HelpdeskTicket(
                ticketDto.getId(),
                ticketDto.getTicketNo(),
                ticketDto.getTitle(),
                ticketDto.getBody(),
                EmployeeMapper.mapToEmployee(ticketDto.getAssignee()),
                ticketDto.getStatus(),
                ticketDto.getCreatedDate(),
                ticketDto.getCreatedBy(),
                ticketDto.getUpdatedDate(),
                ticketDto.getUpdatedBy(),
                ticketDto.getRemarks()
        );
    }
}
