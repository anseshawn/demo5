package com.spring.demo5.service;

import com.spring.demo5.dao.UserDAO;
import com.spring.demo5.domain.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

  final private UserDAO userDAO;

  public List<UserDTO> getUserList() {
    return userDAO.getUserList();
  }

  public int setUserInput(UserDTO dto) {
    return userDAO.setUserInput(dto);
  }

  public UserDTO getUserSearch(String mid) {
    return userDAO.getUserSearch(mid);
  }

  public int setUserDelete(int idx) {
    return userDAO.setUserDelete(idx);
  }
}
