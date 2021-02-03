package com.example.backup.study.service;

import com.example.backup.study.domain.entity.Board;
import com.example.backup.study.dto.BoardDto;
import com.example.backup.study.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class BoardServiceIntegrationTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;

    @Test
    public void 게시믈_글쓰기() throws Exception{

        BoardDto boardDto = new BoardDto();
        boardDto.setId(23L);
        boardDto.setWriter("이하늘");
        boardDto.setTitle("테스트");
        boardDto.setContent("테스트 중입니다.");
        Long saveId = boardService.savePost(boardDto);
        Board findBoard = boardRepository.findById(saveId).get();
        assertEquals(boardDto.getTitle(), findBoard.getTitle());
    }

    @Test
    public void 게시글_조회() throws Exception{

        BoardDto boardDto = BoardDto.builder()
                .id(1L)
                .title("테스트")
                .writer("이하늘")
                .content("테스트 중입니다.")
                .build();

        BoardDto boardDto2 = BoardDto.builder()
                .id(2L)
                .title("테스트")
                .writer("베트맨")
                .content("테스트 중입니다.")
                .build();
        boardService.savePost(boardDto);
        boardService.savePost(boardDto2);

        Long count = boardService.getBoardCount();
        assertThat(count).isEqualTo(2);

    }

    @Test
    public void 게시글_수정() throws Exception{


    }

}
