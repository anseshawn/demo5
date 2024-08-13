package com.spring.demo5.controller;

import com.spring.demo5.domain.UserDTO;
import com.spring.demo5.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

  final private HomeService homeService;

/*
  public HomeController(HomeService homeService) {
    this.homeService = homeService;
  }
*/

  @GetMapping("/")
  public String test1Get() {
    return "index";
  }
/*

  @GetMapping("/userInput")
  public String userInputGet() {
    return "user/userInput";
  }

  @GetMapping("/userList")
  public String userListGet(Model model) {
    List<UserDTO> vos = homeService.getUserList();
    model.addAttribute("vos", vos);
    System.out.println("vos : "+vos);
    return "user/userList";
  }

  @ResponseBody
  @PostMapping("/userDelete")
  public String userDeletePost(int idx) {
    return homeService.setUserDelete(idx)+"";
  }
*/


  @GetMapping("/upload")
  public String uploadGet() {
    return "user/upload";
  }

  @PostMapping("/upload")
  public String uploadPost(MultipartFile fName, RedirectAttributes rttr) {

    //String realPath = request.getSession().getServletContext().getRealPath("/upload");
    // 파일이름 중복처리를 위해 UUID객체 활용
    UUID uid = UUID.randomUUID();
    String oFileName = fName.getOriginalFilename();
    String sFileName = uid.toString().substring(0,8) + "_" + oFileName;
    log.info("=upload=(원본 파일명)" + oFileName);
    log.info("=upload=(저장 파일명)" + sFileName);

    // 서버에 파일 올리기
    int res = 0;
    try {
      writeFile(fName, sFileName);
      res = 1;
    } catch (Exception e) {
      e.printStackTrace();
    }

    if(res != 0) rttr.addFlashAttribute("message", "업로드 성공!");
    else rttr.addFlashAttribute("message", "업로드 실패~");
    return "redirect:/upload";
  }

  private void writeFile(MultipartFile fName, String sFileName) throws IOException {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String realPath = request.getSession().getServletContext().getRealPath("/upload/");

    FileOutputStream fos = new FileOutputStream(realPath + sFileName);

    //fos.write(fName.getBytes());
    if(fName.getBytes().length != -1) {
      fos.write(fName.getBytes());
    }
    fos.flush();
    fos.close();
  }

  @GetMapping("/uploadList")
  public String uploadListGet(HttpServletRequest request, Model model) {
    String realPath = request.getServletContext().getRealPath("/upload/");
    String[] files = new File(realPath).list();
    model.addAttribute("files", files);
    return "user/uploadList";
  }

  @ResponseBody
  @PostMapping("/fileSelectDelete")
  public String fileSelectDeleteGet(HttpServletRequest request, String delItems) {
    String realPath = request.getServletContext().getRealPath("/upload/");
    delItems = delItems.substring(0, delItems.length()-1);

    String[] fileNames = delItems.split("/");

    for(String fileName : fileNames) {
      String realPathFile = realPath + fileName;
      new File(realPathFile).delete();
    }
    return "1";
  }

}
