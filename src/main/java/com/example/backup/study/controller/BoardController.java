package com.example.backup.study.controller;

import com.example.backup.study.dto.BoardDto;
import com.example.backup.study.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum){
        List<BoardDto> boardDtoList = boardService.getBoardList(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardDtoList);
        model.addAttribute("pageList", pageList);

        return "board/list";
    }

    @GetMapping("/boards/new")
    public String write(){
        return "board/write";
    }

    @PostMapping("/boards/new")
    public String write(BoardDto boardDto){
        boardService.savePost(boardDto);
        return "redirect:/boards/list";
    }

    @GetMapping("/boards/new/{no}")
    public String detail(@PathVariable("no") Long id, Model model){
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("boardDto", boardDto);
        return "board/detail";
    }

    @GetMapping("/boards/new/edit/{no}")
    public String edit(@PathVariable("no") Long id, Model model){
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("boardDto", boardDto);
        return "board/update";
    }

    @PutMapping("/boards/new/edit/{no}")
    public String update(BoardDto boardDto){
        boardService.savePost(boardDto);
        return "redirect:/boards/list";
    }

    @DeleteMapping("/boards/new/{no}")
    public String delete(@PathVariable("no") Long id){
        boardService.deletePost(id);
        return "redirect:/boards/list";
    }

    @GetMapping("/boards/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model){
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
        model.addAttribute("boardList", boardDtoList);
        return "board/list";
    }
}
