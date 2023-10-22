package com.pcmk.dto.project.response.codeconvention;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.codeconvention.CodeConvention;
import com.pcmk.domain.project.codeconvention.CodeConventionItem;
import com.pcmk.domain.project.codeconvention.CodeConventionItemElement;
import com.pcmk.dto.project.request.codeconvention.CodeConventionItemDTO;
import com.pcmk.dto.project.request.codeconvention.CodeConventionItemElementDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionUpdateResponseDTO {

    @JsonProperty("code_conventions")
    List<CodeConventionItemDTO> codeConventionItemDTOList;

    public static CodeConventionUpdateResponseDTO of(CodeConvention codeConvention) {
        List<CodeConventionItemDTO> codeConventionItemDTOList = new ArrayList<>();

        for (CodeConventionItem codeConventionItem : codeConvention.getCodeConventionItems()) {
            List<CodeConventionItemElementDTO> codeConventionItemElementDTOList = new ArrayList<>();

            for (CodeConventionItemElement codeConventionItemElement : codeConventionItem.getCodeConventionItemElementList()) {
                codeConventionItemElementDTOList.add(
                        CodeConventionItemElementDTO.of(codeConventionItemElement.getName(),
                                codeConventionItemElement.getExample()));
            }

            codeConventionItemDTOList.add(
                    CodeConventionItemDTO.of(codeConventionItem.getCategory(), codeConventionItemElementDTOList));
        }

        return CodeConventionUpdateResponseDTO.builder()
                .codeConventionItemDTOList(codeConventionItemDTOList)
                .build();
    }

    @Builder
    private CodeConventionUpdateResponseDTO(List<CodeConventionItemDTO> codeConventionItemDTOList) {
        this.codeConventionItemDTOList = codeConventionItemDTOList;
    }
}
