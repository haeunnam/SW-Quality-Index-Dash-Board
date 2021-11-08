package com.display.swqualityboardbatch.entity.mongo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
@Document(collection = "convention_rate")
public class ConventionRate {
    @Id
    private String id;
    private String system_id;
    private int conventionRate;
    private LocalDateTime createdAt;
    
}