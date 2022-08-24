package com.example.demo.controller;

import com.example.demo.controller.dto.ErrorHandlerDto;
import com.example.demo.jwt.CustomJwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class boardController {

    private final CustomJwtFilter customJwtFilter;

    @GetMapping("/list")
    public ErrorHandlerDto BoardList(HttpServletRequest request) throws IOException, ServletException {
        return customJwtFilter.doFilter(request);
    }

    @GetMapping("/write")
    public ErrorHandlerDto BoardWriteForm(HttpServletRequest request) throws IOException, ServletException {
        return customJwtFilter.doFilter(request);

    }

    @GetMapping("/detail")
    public ErrorHandlerDto BoardDetail(HttpServletRequest request) throws IOException, ServletException {
        return customJwtFilter.doFilter(request);
    }

    @GetMapping("/update")
    public ErrorHandlerDto BoardUpdate(HttpServletRequest request) throws IOException, ServletException {
        return customJwtFilter.doFilter(request);
    }

    @GetMapping("/delete")
    public ErrorHandlerDto BoardDelete(HttpServletRequest request) throws IOException, ServletException {
        return customJwtFilter.doFilter(request);
    }


}
