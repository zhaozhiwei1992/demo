package com.lx.demo.springbootmapstruct.service.mapper;

import com.lx.demo.springbootmapstruct.domain.Car;
import com.lx.demo.springbootmapstruct.service.dto.CarDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-11-06 下午3:24
 */
@SpringBootTest
class CarMapperTest {


    @Test
    public void shouldMapCarToDto() {
        //given
        Car car = new Car("Morris", 5);

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);

        //then
        assertThat(carDto).isNotNull();
        assertThat(carDto.getMake()).isEqualTo("Morris");
        assertThat(carDto.getSeatCount()).isEqualTo(5);
        System.out.println("座位数:" + carDto.getSeatCount());
    }

}