package com.review.review.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

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
