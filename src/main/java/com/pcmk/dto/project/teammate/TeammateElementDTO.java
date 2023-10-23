package com.pcmk.dto.project.teammate;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeammateElementDTO {

    private String id;
    @NotBlank(message = "팀원 이름을 입력하세요")
    private String name;
    @NotBlank(message = "팀원 포지션을 입력하세요")
    private String position;
    @NotBlank(message = "링크를 입력하세요")
    private String link;

    public static TeammateElementDTO of(String id, String name, String position, String link) {
        return TeammateElementDTO.builder()
                .id(id)
                .name(name)
                .position(position)
                .link(link)
                .build();
    }

    @Builder
    private TeammateElementDTO(String id, String name, String position, String link) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.link = link;
    }
}
