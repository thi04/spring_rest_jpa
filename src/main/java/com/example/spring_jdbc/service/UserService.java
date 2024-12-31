package com.example.spring_jdbc.service;
import com.example.spring_jdbc.dto.request.UserCreationRequest;
import com.example.spring_jdbc.dto.request.UserUpdateRequest;
import com.example.spring_jdbc.dto.response.UserResponse;
import com.example.spring_jdbc.entity.User;
import com.example.spring_jdbc.exception.AppException;
import com.example.spring_jdbc.exception.ErrorCode;
import com.example.spring_jdbc.mapper.UserMapper;
import com.example.spring_jdbc.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)// làm cho định dạng code bỏ private và ...

public class UserService {

   private final UserRepository userRepository;


    private final  UserMapper userMapper;

    public User createUser(UserCreationRequest request) {
       if (userRepository.existsByUsername(request.getUsername()))//kiểm tra user có tồn tại hay ko??
         throw new AppException(ErrorCode.USER_EXISTED);

         User user=userMapper.toUser(request);
         return userRepository.save(user);

    }



   public UserResponse updateUser(Long userId, UserUpdateRequest request){

           User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("đang có lỗi ở dòng này"));

                userMapper.updateUser(user,request);

               return userMapper.toUserResponse(userRepository.save(user));



    }
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }




public List<User>getUser(){

        return userRepository.findAll();
}

  public UserResponse getUser(Long id){

    User user =    userRepository.findById(id).get();
        return UserResponse.builder()
                .id(id)
                .dob(user.getDob())
                .firstName(user.getFirstName())
                .build();
//        return userMapper.toUserResponse(userRepository.findById(id)
//        .orElseThrow(()-> new RuntimeException("dang có lỗi ở dòng này")));
}





}
