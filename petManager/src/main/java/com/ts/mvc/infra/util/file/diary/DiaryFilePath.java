package com.ts.mvc.infra.util.file.diary;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
//@DynamicInsert // insert 쿼리를 생성할 때 null인 필드는 쿼리에서 생략
//@DynamicUpdate // entity에서 변경이 발견되지 않은 값은 쿼리에서 생략
//@Builder @NoArgsConstructor @AllArgsConstructor @Getter
//@EqualsAndHashCode
public class DiaryFilePath {

//	@Id
//	@GeneratedValue
//	private Long fpIdx;
//	
//	private String originFileName;
//	private String renameFileName;
//	private String savePath;
//	
//	@Column(columnDefinition = "timestamp default now()")
//	private LocalDateTime regDate;
//	
//	@ColumnDefault("false")
//	private Boolean isDel;
//	
//	private String groupName;
}
