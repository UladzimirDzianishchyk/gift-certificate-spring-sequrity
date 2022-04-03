package com.epam.esm.DTO;


import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;


@Data
public class UserDto extends RepresentationModel<OrderDTO> {

    private Long id;

    private String userName;

    private boolean isActive;

    private LocalDateTime registrationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDto userDto = (UserDto) o;

        if (isActive != userDto.isActive) return false;
        if (!id.equals(userDto.id)) return false;
        if (userName != null ? !userName.equals(userDto.userName) : userDto.userName != null) return false;
        return registrationDate != null ? registrationDate.equals(userDto.registrationDate) : userDto.registrationDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        return result;
    }
}
