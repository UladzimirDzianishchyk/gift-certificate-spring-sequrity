package com.epam.esm.DTO;

import com.epam.esm.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class OrderDTO extends RepresentationModel<OrderDTO> {

    private Long id;

    @JsonIgnoreProperties("tags")
    private GiftCertificateDtoWithTags certificate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.ms")
    private LocalDateTime orderDate;

    private Double price;

    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        if (!id.equals(orderDTO.id)) return false;
        if (certificate != null ? !certificate.equals(orderDTO.certificate) : orderDTO.certificate != null)
            return false;
        if (orderDate != null ? !orderDate.equals(orderDTO.orderDate) : orderDTO.orderDate != null) return false;
        if (price != null ? !price.equals(orderDTO.price) : orderDTO.price != null) return false;
        return status == orderDTO.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
