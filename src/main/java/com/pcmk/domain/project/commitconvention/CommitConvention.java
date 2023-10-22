package com.pcmk.domain.project.commitconvention;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommitConvention {

    List<CommitConventionElement> elements;

    public static CommitConvention of(List<CommitConventionElement> elements) {
        return CommitConvention.builder()
                .elements(elements)
                .build();
    }

    @Builder
    private CommitConvention(List<CommitConventionElement> elements) {
        this.elements = elements;
    }
}
