package com.ts.mvc.module.guestbook;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ts.mvc.module.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DynamicInsert // insert 쿼리를 생성할 때 null인 필드는 쿼리에서 생략
@DynamicUpdate // entity에서 변경이 발견되지 않은 값은 쿼리에서 생략
@Builder @NoArgsConstructor @AllArgsConstructor @Getter
public class GuestBook {
	
	@Id
	@GeneratedValue
	private Long gbIdx;
	
	private String content;
	
	@Column(columnDefinition = "timestamp default now()")
	private LocalDateTime regDate;
	
	@ManyToOne
	private User user; // 수신자
	
	@ManyToOne
	private User host; // 발신자
	
}
