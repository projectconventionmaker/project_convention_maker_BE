package com.pcmk.domain.project.commitconvention;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommitConventionItem {
    private String name;
    private boolean checked;
}
