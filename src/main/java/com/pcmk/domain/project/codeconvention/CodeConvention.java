package com.pcmk.domain.project.codeconvention;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeConvention {
    List<CodeConventionItem> items;
}
