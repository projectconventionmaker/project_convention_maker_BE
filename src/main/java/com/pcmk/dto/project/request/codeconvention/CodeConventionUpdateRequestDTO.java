package com.pcmk.dto.project.request.codeconvention;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pcmk.domain.project.codeconvention.CodeConvention;
import com.pcmk.domain.project.codeconvention.CodeConventionItem;
import com.pcmk.domain.project.codeconvention.CodeConventionItemElement;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionUpdateRequestDTO {

    @NotNull
    @JsonProperty("code_conventions")
    private List<CodeConventionItemDTO> codeConventionItemDTOList;

    public List<CodeConventionItem> toCodeConventionItemList() {
        List<CodeConventionItem> itemList = new ArrayList<>();

        for (CodeConventionItemDTO itemDTO : codeConventionItemDTOList) {
            List<CodeConventionItemElement> codeConventionItemElementList = itemDTO.toCodeConventionItemElementList();

            itemList.add(CodeConventionItem.of(itemDTO.getCategory(), codeConventionItemElementList));
        }

        return itemList;
    }

    public static CodeConventionUpdateRequestDTO of(CodeConvention codeConvention) {
        List<CodeConventionItemDTO> itemDTOList = new ArrayList<>();

        for (CodeConventionItem item : codeConvention.getCodeConventionItems()) {
            List<CodeConventionItemElementDTO> elementDTOList = new ArrayList<>();

            for (CodeConventionItemElement element : item.getCodeConventionItemElementList()) {
                elementDTOList.add(
                        CodeConventionItemElementDTO.of(element.getName(), element.getName()));
            }

            itemDTOList.add(CodeConventionItemDTO.of(item.getCategory(), elementDTOList));
        }

        return CodeConventionUpdateRequestDTO.builder()
                .codeConventionItemDTOList(itemDTOList)
                .build();
    }

    @Builder
    private CodeConventionUpdateRequestDTO(List<CodeConventionItemDTO> codeConventionItemDTOList) {
        this.codeConventionItemDTOList = codeConventionItemDTOList;
    }
}
