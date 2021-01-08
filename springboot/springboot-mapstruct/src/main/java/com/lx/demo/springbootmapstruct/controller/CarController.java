package com.lx.demo.springbootmapstruct.controller;

import com.lx.demo.springbootmapstruct.domain.Car;
import com.lx.demo.springbootmapstruct.service.dto.CarDto;
import com.lx.demo.springbootmapstruct.service.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-11-06 下午3:59
 */
@RestController
public class CarController {

    final List<Car> cars = Arrays.asList(new Car("Morris", 5)
            , new Car("Morris", 5)
            , new Car("Morris", 5));

    @Autowired
    private CarMapper carMapper;

    /**
     * @data: 2021/1/8-下午3:27
     * @User: zhaozhiwei
     * @method: carDtoList

     * @return: java.util.List<com.lx.demo.springbootmapstruct.service.dto.CarDto>
     * @Description: 将一堆car转成cardtos
     * curl -X GET http://127.0.0.1:8080/cardtos
     */
    @GetMapping("/cardtos")
    public List<CarDto> carDtoList(){
        final List<CarDto> carDtos = carMapper.carsToCarDtos(cars);
        return carDtos;
    }

    @GetMapping("/cars")
    public List<Car> carList(){
        return cars;
    }
}
