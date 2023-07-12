package com.novmah.blog.services.impl;

import com.novmah.blog.config.AppConstants;
import com.novmah.blog.entities.Role;
import com.novmah.blog.entities.User;
import com.novmah.blog.exceptions.ResourceNotFoundException;
import com.novmah.blog.payloads.UserDto;
import com.novmah.blog.repositories.RoleRepo;
import com.novmah.blog.repositories.UserRepo;
import com.novmah.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findById(AppConstants.ROLE_USER).get();
        user.getRoles().add(role);
        userRepo.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        userRepo.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));

        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepo.findAll();

        return users.stream().map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));
        userRepo.delete(user);

    }
}
