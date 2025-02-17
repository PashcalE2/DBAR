package main.isbd.data.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInOrderInfo {
    private Integer id;
    private String status;
    private Integer count;
}
