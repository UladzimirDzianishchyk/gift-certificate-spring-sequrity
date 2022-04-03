package com.epam.esm.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
public class TagDto extends RepresentationModel<TagDto> {

    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TagDto tagDto = (TagDto) o;

        if (id != null ? !id.equals(tagDto.id) : tagDto.id != null) return false;
        return name != null ? name.equals(tagDto.name) : tagDto.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
