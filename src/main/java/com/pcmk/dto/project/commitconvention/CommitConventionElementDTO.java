package com.pcmk.dto.project.commitconvention;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommitConventionElementDTO {
    @NotBlank
    private String name;
    @NotNull
    private boolean checked;

    public static CommitConventionElementDTO of(String name, boolean checked) {
        return CommitConventionElementDTO.builder()
                .name(name)
                .checked(checked)
                .build();
    }

    @Builder
    private CommitConventionElementDTO(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }
}
