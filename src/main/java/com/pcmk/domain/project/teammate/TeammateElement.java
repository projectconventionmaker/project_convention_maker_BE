package com.pcmk.domain.project.teammate;

import com.pcmk.utils.UUIdUtils;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeammateElement {

    private String id;
    private String name;
    private String position;
    private String link;

    public static TeammateElement of(String name, String position, String link) {
        return TeammateElement.builder()
                .id(UUIdUtils.createUUID())
                .name(name)
                .position(position)
                .link(link)
                .build();
    }

    @Builder
    private TeammateElement(String id, String name, String position, String link) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.link = link;
    }
}
