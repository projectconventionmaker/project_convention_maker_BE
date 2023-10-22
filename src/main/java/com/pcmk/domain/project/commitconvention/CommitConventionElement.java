package com.pcmk.domain.project.commitconvention;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommitConventionElement {
    private String name;
    private boolean checked;

    public static CommitConventionElement of(String name, boolean checked) {
        return CommitConventionElement.builder()
                .name(name)
                .checked(checked)
                .build();
    }

    @Builder
    private CommitConventionElement(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }
}
