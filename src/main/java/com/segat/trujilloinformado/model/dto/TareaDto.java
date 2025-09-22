package com.segat.trujilloinformado.model.dto;

import com.segat.trujilloinformado.model.entity.enums.Priority;
import com.segat.trujilloinformado.model.entity.enums.Status;
import com.segat.trujilloinformado.model.entity.enums.Type;
import com.segat.trujilloinformado.model.entity.interno.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TareaDto {
    private Long id;
    private Long reportId;
    private Long workerId;
    private Long supervisorId;
    private String title;
    private String description;
    private String notes;
    private Type type;
    private Location location;
    private Status status;
    private Priority priority;
    private String completedAt;
}
