package com.example.springbootwebsite.web;
import javax.servlet.http.HttpSession;

import com.example.springbootwebsite.config.auth.LoginUser;
import com.example.springbootwebsite.config.auth.dto.SessionUser;
import com.example.springbootwebsite.service.posts.PostsService;
import com.example.springbootwebsite.web.dto.PostsResponseDto;
import com.example.springbootwebsite.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";

    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findByID(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }


}

