package com.mc.mvc.module.board.dto.resonse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.mc.mvc.module.board.Board;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardListResponse {

	private Long bdIdx;
	private String userId;
	private String title;
	private LocalDateTime regDate;

	public BoardListResponse(Board entity) {
		this.bdIdx = entity.getBdIdx();
		this.title = entity.getTitle();
		this.regDate = entity.getRegDate();
		this.userId = entity.getMember().getUserId();
	}
	
	public String getRegDateAsDate() {
		return regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public String getRegDateAsTime() {
		return regDate.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
	
	public static List<BoardListResponse> toDtoList(List<Board> entityList){
		return entityList.stream().map(e -> new BoardListResponse(e)).collect(Collectors.toList());
	}

	
	
	
	
	
	
	
	
	
	
}
