package com.pcmk.domain.project.techstack;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TechStack {

    private List<TechStackElement> techStackElmentList;

    public static TechStack of(List<TechStackElement> techStackElementList) {
        return TechStack.builder()
                .techStackElmentList(techStackElementList)
                .build();
    }

    @Builder
    private TechStack(List<TechStackElement> techStackElmentList) {
        this.techStackElmentList = techStackElmentList;
    }
}
