package com.example.Book.Management.service;

import com.example.Book.Management.dto.UserDto;
import com.example.Book.Management.entity.Role;
import com.example.Book.Management.entity.User;
import com.example.Book.Management.entity.UserInfo;
import com.example.Book.Management.exception.InvalidJwtToken;
import com.example.Book.Management.exception.InvalidRequestBody;
import com.example.Book.Management.exception.UserAlreadyExist;
import com.example.Book.Management.exception.UserNotFound;
import com.example.Book.Management.jwt.JwtAuthenticationHelper;
import com.example.Book.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JwtAuthenticationHelper jwtAuthenticationHelper;

    public User getUserByEmail(String email)throws Exception{
        User user = (User)userRepository.findByEmail(email).get();
        if(user == null){
            throw new UserNotFound("User with given id not found");
        }
        return  user;
    }

    public Boolean createUser(UserDto userDto) throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userDto.getPassword());
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
            throw  new UserAlreadyExist("user with given email"+ userDto.getEmail()+" already exist");
        }
        userDto.setPassword(encodedPassword);
        User user = mapUserDtoToUser(userDto);

        user = userRepository.save(user);
        if(user == null){
            throw new InvalidRequestBody("Invalid request to register user");
        }
        return true;

    }

    //get the user profile
    public UserDto getMyProfile(String jwtToken) {
        String email = jwtAuthenticationHelper.getUsernameFromToken(jwtToken.substring(7));
        User user = (User)userRepository.findByEmail(email).get();
        if(user != null && user.getUserInfo() !=null){
            return UserDto.builder()
                    .email(user.getEmail())
                    .address(user.getUserInfo().getAddress())
                    .phoneNo(user.getUserInfo().getPhoneNO())
                    .dob(user.getUserInfo().getDob())
                    .name(user.getName())
                    .education(user.getUserInfo().getEducation())
                    .institution(user.getUserInfo().getInstitutionName())
                    .institutionId(user.getUserInfo().getInstitutionId())
                    .build();
        }
        else if(user != null){
            return UserDto.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .build();
        }
        else
            throw new InvalidJwtToken("Invalid jwt Token");

    }

    // get user profile by id
    public UserDto getUserProfileById(Long userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return mapUserToUserDto(user.get());
        }
       else
         throw new UserNotFound("User with given id not found");
    }



// update the user
    public void updateUserDetails(UserDto userDto, Long userId) throws Exception{
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            User updateUser = user.get();
            Boolean noDataGiven = true;
            if(userDto.getEmail().isBlank()){
                updateUser.setEmail(updateUser.getEmail());
                noDataGiven=false;
            }
            if (userDto.getName().isBlank()){
                updateUser.setName(updateUser.getName());
                noDataGiven=false;
            }
            if(noDataGiven) {
                throw new RuntimeException("No Data present given Request Body to update User with given id");
            }
            updateUser =userRepository.save(updateUser);
            if(updateUser == null){
                throw new InvalidRequestBody("Invalid request to register user");
            }
        }
        else {
            throw new UserNotFound("User with given id not found");
        }

    }

    public void deleteUserDetails(Long userId)throws Exception {
         User user = userRepository.findById(userId).get();
         if(user == null){
             throw new UserNotFound("User with given id not found");
         }
         userRepository.delete(user);
    }

    //update the user profile by user
    public Boolean updateUserProfile(String jwtToken, UserDto userDto) throws  Exception{
          String email = jwtAuthenticationHelper.getUsernameFromToken(jwtToken);
          User user = getUserByEmail(email);
          user.setName(userDto.getName());
        UserInfo userInfo = UserInfo.builder()
                .institutionId(userDto.getInstitutionId())
                .dob(userDto.getDob())
                .Address(userDto.getAddress())
                .education(userDto.getEducation())
                .PhoneNO(userDto.getPhoneNo())
                .institutionName(userDto.getInstitution())
                .yearOfGraduation(userDto.getYearofgraduation())
                .build();
        userInfo.setUser(user);
        if(user.getUserInfo()!=null)
            userInfo.setUserInfoId(user.getUserInfo().getUserInfoId());
        user.setUserInfo(userInfo);
        user = userRepository.save(user);
        if (user == null){
            throw new InvalidRequestBody("invalid RequestBody");
        }
        return true;
    }





    public UserDto mapUserToUserDto(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public User mapUserDtoToUser(UserDto userDto){
        return   User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .role(Role.ROLE_STUDENT.getAuthority())
                .password(userDto.getPassword())
                .build();
    }


    //retrive all the users by admin / librarian
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(
                user -> {
                   UserDto userDto =   UserDto.builder()
                            .userId(user.getId())
                            .name(user.getName())
                            .role(user.getRole().substring(5))
                            .build();

                   if (user.getUserInfo() != null){
                       userDto.setYearofgraduation(user.getUserInfo().getYearOfGraduation());
                   }
                   return userDto;
                }
        ).collect(Collectors.toList());
    }
}
