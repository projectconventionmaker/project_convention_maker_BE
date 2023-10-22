package com.pcmk.dto.project.teammate;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeammateElementDTO {

    @NotBlank(message = "팀원 이름을 입력하세요")
    private String name;
    @NotBlank(message = "팀원 포지션을 입력하세요")
    private String position;
    @NotBlank(message = "링크를 입력하세요")
    private String link;

    public static TeammateElementDTO of(String name, String position, String link) {
        return TeammateElementDTO.builder()
                .name(name)
                .position(position)
                .link(link)
                .build();
    }

    @Builder
    private TeammateElementDTO(String name, String position, String link) {
        this.name = name;
        this.position = position;
        this.link = link;
    }
}
