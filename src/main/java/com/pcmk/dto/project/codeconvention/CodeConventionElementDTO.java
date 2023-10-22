package com.pcmk.dto.project.codeconvention;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.codeconvention.CodeConventionElementItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionElementDTO {

    @NotBlank
    private String category;

    @NotNull
    @JsonProperty("items")
    private List<CodeConventionElementItemDTO> codeConventionElementItemDTOs;

    public List<CodeConventionElementItem> toCodeConventionItemElementList() {
        List<CodeConventionElementItem> elementList = new ArrayList<>();

        for (CodeConventionElementItemDTO elementDTO : codeConventionElementItemDTOs) {
            elementList.add(CodeConventionElementItem.of(elementDTO.getName(),
                    elementDTO.getExample()));
        }

        return elementList;
    }

    public static CodeConventionElementDTO of(String category,
                                              List<CodeConventionElementItemDTO> codeConventionElementItemDTOs) {
        return CodeConventionElementDTO.builder()
                .category(category)
                .codeConventionElementItemDTOs(codeConventionElementItemDTOs)
                .build();
    }

    @Builder
    private CodeConventionElementDTO(String category,
                                     List<CodeConventionElementItemDTO> codeConventionElementItemDTOs) {
        this.category = category;
        this.codeConventionElementItemDTOs = codeConventionElementItemDTOs;
    }
}
