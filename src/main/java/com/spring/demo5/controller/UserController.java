package com.spring.demo5.controller;

import com.spring.demo5.domain.UserDTO;
import com.spring.demo5.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  // 로그찍기 1.롬복 올리기 2.Slf4j어노테이션 올리기 3. log사용(info, warn, debug, error, trace(정보량이 많기 때문에 가급적 찍지 말것))
  final private UserService userService;

  private String message = "";
  private String viewPage = "";

  // 회원 리스트보기
  @GetMapping("/userList")
  public String userListGet(Model model) {
    log.info("userList"); // sysout은 콘솔에만, log로 찍는 것은 기록으로 남아있는다
    List<UserDTO> vos = userService.getUserList();
    model.addAttribute("vos", vos);
    //System.out.println("vos : " + vos);
    return "user/userList";
  }

  // user 삭제
  @ResponseBody
  @PostMapping("/userDelete")
  public String userDeletePost(int idx) {
    System.out.println("userDelete");
    return userService.setUserDelete(idx) + "";
  }


  // user 등록창 보기
  @GetMapping("/userInput")
  public String userInputGet() {
    return "user/userInput";
  }

  // user 등록하기(RedirectAttributes : 브라우저 주소창에 주소를 입력하고 엔터를 누른 것처럼 URL도 바뀌는 서버측의 행동)
  @PostMapping("/userInput")
  public String userInputPost(UserDTO dto, RedirectAttributes rttr) {
    int res = userService.setUserInput(dto);

    if(res == 1) {
      message = "회원자료가 등록 되었습니다.";
      viewPage = "redirect:/user/userList";
    }
    else {
      message = "회원자료 등록 실패~~";
      viewPage = "redirect:/user/userInput";
    }
    // addFlashAttribute : 1회성 저장처리(view까지 간다. 그러나 model은 다음 호출시까지만 유효함)
    rttr.addFlashAttribute("message", message);
    return viewPage;
  }

  // 개별자료 검색하기
  @PostMapping("/userList")
  public String userListPost(RedirectAttributes rttr,
    @RequestParam(name="flagSw", defaultValue = "", required = false) String flagSw,
    @RequestParam(name="mid", defaultValue = "", required = false) String mid) {
    log.info("=userList=");
    UserDTO dto = userService.getUserSearch(mid);
    String message = "";
    if(dto == null) message = "검색된 자료가 없습니다.";
    // addAttribute : 가져가서 계속 사용, addFlashAttribute: 가지고 넘어가서 화면에서 한번 쓰고 버림(메세지가 다시 뜨지 않는다)
    rttr.addFlashAttribute("message", message);
    rttr.addFlashAttribute("flagSw", flagSw);
    rttr.addFlashAttribute("dto", dto);
    return "redirect:/user/userList";
  }

}
