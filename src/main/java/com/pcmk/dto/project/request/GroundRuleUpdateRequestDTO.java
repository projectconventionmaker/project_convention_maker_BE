package com.pcmk.dto.project.request;

import com.pcmk.domain.project.groundrule.GroundRuleItem;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GroundRuleUpdateRequestDTO {
    @NotNull
    List<GroundRuleItem> items;
}
