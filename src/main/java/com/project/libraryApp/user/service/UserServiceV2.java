package com.project.libraryApp.user.service;

import com.project.libraryApp.user.domain.User;
import com.project.libraryApp.user.domain.UserRepository;
import com.project.libraryApp.user.dto.request.UserCreateRequest;
import com.project.libraryApp.user.dto.request.UserUpdateRequest;
import com.project.libraryApp.user.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 등록 메소드
    public void saveUser(UserCreateRequest request){
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    // 사용자 목록 조회 메소드
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    // 사용자 이름 변경 메소드
    public void updateUser(UserUpdateRequest request){
        // User가 있다면 Optional<User>로 결과 반환, User가 없으면 Exception을 발생시킴
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
        userRepository.save(user);
    }

    // 사용자 삭제 메소드
    public void deleteUser(String name){
        User user = userRepository.findByName(name);
        if(user == null){
            throw new IllegalArgumentException();
        }
        userRepository.delete(user);
    }

}
