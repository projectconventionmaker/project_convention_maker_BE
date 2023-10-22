package com.pcmk.dto.project.request.codeconvention;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.codeconvention.CodeConventionItemElement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionItemDTO {

    @NotBlank
    private String category;

    @NotNull
    @JsonProperty("items")
    private List<CodeConventionItemElementDTO> codeConventionItemElementDTOList;

    public List<CodeConventionItemElement> toCodeConventionItemElementList() {
        List<CodeConventionItemElement> elementList = new ArrayList<>();

        for (CodeConventionItemElementDTO elementDTO : codeConventionItemElementDTOList) {
            elementList.add(CodeConventionItemElement.of(elementDTO.getName(),
                    elementDTO.getExample()));
        }

        return elementList;
    }

    public static CodeConventionItemDTO of(String category,
                                           List<CodeConventionItemElementDTO> codeConventionItemElementDTOList) {
        return CodeConventionItemDTO.builder()
                .category(category)
                .codeConventionItemElementDTOList(codeConventionItemElementDTOList)
                .build();
    }

    @Builder
    private CodeConventionItemDTO(String category,
                                  List<CodeConventionItemElementDTO> codeConventionItemElementDTOList) {
        this.category = category;
        this.codeConventionItemElementDTOList = codeConventionItemElementDTOList;
    }
}
