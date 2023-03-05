package com.example.swagger.service;

import com.example.swagger.common.exceptions.NotFoundException;
import com.example.swagger.dtos.CreateUserDto;
import com.example.swagger.dtos.UserDto;
import com.example.swagger.entity.User;
import com.example.swagger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDTOs = new ArrayList<>();
        for (User user:users) {
//            var dto = new UserDto(user.getId(), user.getName(), user.getAge(), user.getSalary());
            var dto = new UserDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setAge(user.getAge());
            dto.setSalary(user.getSalary());
            userDTOs.add(dto);
        }

        return userDTOs;
    }
    public UserDto createUser(CreateUserDto userDto) {
        var user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setSalary(userDto.getSalary());
        userRepository.save(user);

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setSalary(user.getSalary());
        return dto;
    }

    public UserDto updateUser(UUID id, UserDto userDto) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setSalary(userDto.getSalary());
        userRepository.save(user);
        return userDto;
    }

    public void deleteUser(UUID id) {
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
    }

}
