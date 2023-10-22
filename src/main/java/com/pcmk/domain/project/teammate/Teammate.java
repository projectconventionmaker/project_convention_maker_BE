package com.pcmk.domain.project.teammate;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Teammate {

    List<TeammateElement> elements;

    public static Teammate of(List<TeammateElement> elements) {
        return Teammate.builder()
                .elements(elements)
                .build();
    }

    @Builder
    private Teammate(List<TeammateElement> elements) {
        this.elements = elements;
    }
}
