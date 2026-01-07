package com.company.company.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long companyId;
    private String companyName;
    private String industry;
    private String description;
    private String email;
    private String location;
    private String eventType; // CREATED, UPDATED, DELETED

}
