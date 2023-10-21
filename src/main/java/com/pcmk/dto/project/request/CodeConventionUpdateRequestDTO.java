package com.pcmk.dto.project.request;

import com.pcmk.domain.project.codeconvention.CodeConventionItem;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConventionUpdateRequestDTO {
    @NotNull
    List<CodeConventionItem> items;

    public static CodeConventionUpdateRequestDTO of(List<CodeConventionItem> items) {
        return CodeConventionUpdateRequestDTO.builder()
                .items(items)
                .build();
    }

    @Builder
    private CodeConventionUpdateRequestDTO(List<CodeConventionItem> items) {
        this.items = items;
    }
}
