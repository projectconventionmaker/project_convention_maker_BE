package com.pcmk.dto.project.request;

import com.pcmk.domain.project.commitconvention.CommitConventionItem;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommitConventionUpdateRequestDTO {
    @NotNull
    List<CommitConventionItem> items;

    @Builder
    private CommitConventionUpdateRequestDTO(List<CommitConventionItem> items) {
        this.items = items;
    }
}
