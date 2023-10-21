package com.pcmk.domain.project.commitconvention;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommitConvention {
    List<CommitConventionItem> items;
}
