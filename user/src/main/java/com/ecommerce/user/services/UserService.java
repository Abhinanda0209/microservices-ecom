package com.ecommerce.user.services;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<UserResponse> getAllUsers() {
        List<User> usersList = userRepository.findAll();

        List<UserResponse> userResponseList = usersList.stream()
                .map(user -> mapper.map(user, UserResponse.class))
                .toList();

        return userResponseList;
    }

    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapper.map(user, UserResponse.class);
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        userRepository.save(user);

        return mapper.map(user, UserResponse.class);
    }

    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        User userFromDB = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userFromDB.setFirstName(userRequest.getFirstName());
        userFromDB.setLastName(userRequest.getLastName());
        userFromDB.setEmail(userRequest.getEmail());
        userFromDB.setPhone(userRequest.getPhone());
        userFromDB.setAddress(mapper.map(userRequest.getAddress(), Address.class));

        userRepository.save(userFromDB);

        return mapper.map(userFromDB, UserResponse.class);
    }
}
