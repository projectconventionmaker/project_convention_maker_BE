package com.pcmk.domain.project.commitconvention;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommitConvention {
    List<CommitConventionItem> items;

    public static CommitConvention of(List<CommitConventionItem> items) {
        return CommitConvention.builder()
                .items(items)
                .build();
    }

    @Builder
    private CommitConvention(List<CommitConventionItem> items) {
        this.items = items;
    }
}
