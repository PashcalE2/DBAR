package main.isbd.data.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    private Integer id;
    private String name;
    private Float price;
    private String description;
}
