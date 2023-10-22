package com.pcmk.dto.project.codeconvention;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.codeconvention.CodeConvention;
import com.pcmk.domain.project.codeconvention.CodeConventionElement;
import com.pcmk.domain.project.codeconvention.CodeConventionElementItem;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionUpdateRequestDTO {

    @NotNull
    @JsonProperty("code_conventions")
    private List<CodeConventionElementDTO> elementDTOs;

    public CodeConvention toEntity() {
        List<CodeConventionElement> elements = elementDTOs.stream()
                .map(dto -> CodeConventionElement.of(dto.getCategory(),
                        dto.getCodeConventionElementItemDTOs().stream()
                                .map(itemDTO -> CodeConventionElementItem.of(itemDTO.getName(), itemDTO.getExample()))
                                .toList())
                ).toList();

        return CodeConvention.of(elements);
    }

    public static CodeConventionUpdateRequestDTO of(List<CodeConventionElementDTO> elementDTOs) {
        return CodeConventionUpdateRequestDTO.builder()
                .elementDTOs(elementDTOs)
                .build();
    }

    @Builder
    private CodeConventionUpdateRequestDTO(List<CodeConventionElementDTO> elementDTOs) {
        this.elementDTOs = elementDTOs;
    }
}
