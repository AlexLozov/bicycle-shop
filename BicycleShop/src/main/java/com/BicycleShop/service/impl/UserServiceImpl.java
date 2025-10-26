package com.BicycleShop.service.impl;

import com.BicycleShop.mapper.UserMapper;
import com.BicycleShop.model.constants.ApiErrorMessage;
import com.BicycleShop.model.dto.bicycle.BicycleSearchDTO;
import com.BicycleShop.model.dto.user.FullUserDTO;
import com.BicycleShop.model.dto.user.UserDTO;
import com.BicycleShop.model.dto.user.UserSearchDTO;
import com.BicycleShop.model.entities.Role;
import com.BicycleShop.model.entities.ShoppingCart;
import com.BicycleShop.model.entities.User;
import com.BicycleShop.model.exception.DataExistException;
import com.BicycleShop.model.exception.NotFoundException;
import com.BicycleShop.model.request.user.NewUserRequest;
import com.BicycleShop.model.request.user.UserSearchRequest;
import com.BicycleShop.model.response.IamResponse;
import com.BicycleShop.model.response.PaginationResponse;
import com.BicycleShop.repositories.RoleRepository;
import com.BicycleShop.repositories.ShoppingCartRepository;
import com.BicycleShop.repositories.UserRepository;
import com.BicycleShop.repositories.criteria.UserSearchCriteria;
import com.BicycleShop.security.validator.AccessValidator;
import com.BicycleShop.service.UserService;
import com.BicycleShop.service.model.IamServiceUserRole;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AccessValidator accessValidator;

    @Override
    public IamResponse<FullUserDTO> getFullUserById(@NotNull Integer id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(id)));

        FullUserDTO fullUserDTO = userMapper.toFullDTO(user);
        return IamResponse.createSuccessful(fullUserDTO);
    }

    @Override
    public IamResponse<UserDTO> createUser(@NotNull NewUserRequest newUserRequest) {
        if(userRepository.existsByUsername(newUserRequest.getUsername())) {
            throw new DataExistException(ApiErrorMessage.USER_WITH_NAME_ALREADY_EXISTS.getMessage(newUserRequest.getUsername()));
        }

        if(userRepository.existsByEmail(newUserRequest.getEmail())) {
            throw new DataExistException(ApiErrorMessage.USER_WITH_EMAIL_ALREADY_EXISTS.getMessage(newUserRequest.getEmail()));
        }


        User user = userMapper.createUser(newUserRequest);
        user.setLast_login(LocalDateTime.now());

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        user.setShoppingCart(cart);
        user.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));

        Role role = roleRepository.findByName(IamServiceUserRole.USER.getRole())
                        .orElseThrow(() -> new NotFoundException(ApiErrorMessage.ROLE_WITH_NAME_NOT_FOUND.getMessage(IamServiceUserRole.USER.getRole())));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        UserDTO userDTO = userMapper.toDTO(user);

        return IamResponse.createSuccessful(userDTO);

    }



    @Override
    public IamResponse<UserDTO> getUserById(@NotNull Integer id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(id)));

        UserDTO userDTO = userMapper.toDTO(user);
        return IamResponse.createSuccessful(userDTO);
    }


    @Override
    public void softDeleteUserById(Integer id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(id)));

        user.setDeleted(true);
        userRepository.save(user);
    }



    @Override
    public IamResponse<UserDTO> updateUserById(Integer id, NewUserRequest newUserRequest) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(id)));

        accessValidator.validateAdminOrOwnerAccess(user.getId());

        if(newUserRequest.getEmail() != null && !newUserRequest.getEmail().isBlank()) {
            if(userRepository.existsByEmailAndIdNot(newUserRequest.getEmail(), id)){
                throw new DataExistException(ApiErrorMessage.USER_WITH_EMAIL_ALREADY_EXISTS.getMessage());
            }
            user.setEmail(newUserRequest.getEmail());
        }

        if(newUserRequest.getUsername() != null && !newUserRequest.getUsername().isBlank()) {
            if(userRepository.existsByUsernameAndIdNot(newUserRequest.getUsername(), id)){
                throw new DataExistException(ApiErrorMessage.USER_WITH_NAME_ALREADY_EXISTS.getMessage());
            }
            user.setUsername(newUserRequest.getUsername());
        }

        if(newUserRequest.getPassword() != null && !newUserRequest.getPassword().isEmpty()) {
            user.setPassword(newUserRequest.getPassword());
        }

        UserDTO userDTO = userMapper.toDTO(user);
        userRepository.save(user);


        return IamResponse.createSuccessful(userDTO);
    }




    @Override
    public IamResponse<PaginationResponse<UserSearchDTO>> findAllUsers(Pageable pageable) {
        Page<UserSearchDTO> users = userRepository.findAll(pageable)
                .map(userMapper::toUserSearchDTO);

        PaginationResponse<UserSearchDTO> response = new PaginationResponse<>(
                users.getContent(),
                new PaginationResponse.Pagination(
                        pageable.getPageSize(),
                        users.getTotalPages(),
                        users.getNumber() + 1,
                        users.getTotalElements()
                )
        );

        return IamResponse.createSuccessful(response);
    }

    @Override
    public IamResponse<PaginationResponse<UserSearchDTO>> searchUsers(
            @NotNull UserSearchRequest request,
            Pageable pageable) {

        Specification<User> specification = new UserSearchCriteria(request);

        Page<UserSearchDTO> users = userRepository.findAll(specification, pageable)
                .map(userMapper::toUserSearchDTO);

        PaginationResponse<UserSearchDTO> response = new PaginationResponse<>(
                users.getContent(),
                new PaginationResponse.Pagination(
                        pageable.getPageSize(),
                        users.getTotalPages(),
                        users.getNumber() + 1,
                        users.getTotalElements()
                )
        );

        return IamResponse.createSuccessful(response);
    }

// ---------------- auth-------------
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUserDetails(email, userRepository);
    }

    static  UserDetails getUserDetails(String email, UserRepository userRepository) {
            User user = userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_EMAIL_NOT_FOUND.getMessage()));

            user.setLast_login(LocalDateTime.now());
            userRepository.save(user);

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList())
            );
    }
    //-----------------------------------------------
}
