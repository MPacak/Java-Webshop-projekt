package hr.webshop.mapper;


import hr.webshop.dto.*;
import hr.webshop.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppMapper {
    //product mapper
    @Mapping(target="categoryName", source="category.name")
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toProductDto(Product product);
    Product toProductEntity(ProductDto dto);

    //user mapper
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", source = "role")
    UserDto toUserDto(User user);

    @Mapping(target = "passwordHash", source = "password")
    @Mapping(target = "role", source = "role")
    User toUserEntity(UserDto dto);

    //orderitem mapper
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "imagePath", source = "product.imagePath")
    OrderItemsDto toOrderItemDto(OrderItem orderItem);

    List<OrderItemsDto> toOrderItemDtos(List<OrderItem> items);
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItemEntity(OrderItemsDto dto);
    List<OrderItem> toOrderItemEntities(List<OrderItemsDto> items);

//oredr mapper
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "paymentMethodId", source = "paymentMethod.id")
    @Mapping(target = "paymentMethodName", source ="paymentMethod.paymentName")
    @Mapping(target = "orderItems", source = "orderItems")
    OrderDto toOrderDto(Order order);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "paymentMethod.id", source = "paymentMethodId")
    @Mapping(target = "orderItems", source = "orderItems")
    Order toOrderEntity(OrderDto dto);

    //logs
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    LoginDto toLoginDto(LoginLogs loginLogs);


    //category
    CategoryDto toCategoryDto(Category category);
    Category toCategoryEntity(CategoryDto dto);
    //orderlog
    OrderLogDto toOrderLogDto(OrderLog log);
    OrderLog toOrderLogEntity(OrderLogDto dto);


}
