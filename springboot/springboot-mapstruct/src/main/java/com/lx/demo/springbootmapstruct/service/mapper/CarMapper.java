package com.lx.demo.springbootmapstruct.service.mapper;

import com.lx.demo.springbootmapstruct.domain.Car;
import com.lx.demo.springbootmapstruct.service.dto.CarDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 设置componentModel="spring"后　生产的实现类会加入component注解, 可以通过spring注入
 * {@see https://blog.csdn.net/jtf8525140/article/details/78130601}
 */
@Mapper(componentModel = "spring")
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDto carToCarDto(Car car);

//    @Mapping(source = "numberOfSeats", target = "seatCount")
    List<CarDto> carsToCarDtos(List<Car> cars);
}

