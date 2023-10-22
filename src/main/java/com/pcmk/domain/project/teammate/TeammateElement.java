package com.pcmk.domain.project.teammate;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeammateElement {

    private String name;
    private String position;
    private String link;

    public static TeammateElement of(String name, String position, String link) {
        return TeammateElement.builder()
                .name(name)
                .position(position)
                .link(link)
                .build();
    }

    @Builder
    private TeammateElement(String name, String position, String link) {
        this.name = name;
        this.position = position;
        this.link = link;
    }
}
