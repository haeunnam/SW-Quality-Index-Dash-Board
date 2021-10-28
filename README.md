# S/W 품질지표 Dash Board 시스템 개발

> - 다양한 S/W 시스템 및 모듈에서 전송된 품질 Raw데이터를 활용하여 전사 S/W 품질지표 현황을 한 눈에 확인 가능한 Dash Board 개발



### :bulb: 주요 기능

- 전체 / 시스템 / 팀별 지표 조회
- 통계 그래프 사용
- 기준 미달 지표에 대한 하이라이트 표시
- S/W관련 용어 설명



## 기획



### :bulb: 페르소나 및 사용자 시나리오

[페르소나 정의와 사용자 시나리오 확인하기](https://www.figma.com/file/0ZJIzrgYOiCZTOhIdLfu9P/%ED%92%88%EC%A7%88%EC%8B%9C%EC%8A%A4%ED%85%9C-%EB%8C%80%EC%8B%9C%EB%B3%B4%EB%93%9C-%EA%B0%9C%EB%B0%9C?node-id=0%3A1)



### :bulb: 요구사항 명세서 및 WBS

[요구사항 명세서 및 WBS 확인하기](https://docs.google.com/spreadsheets/d/14IlvOBf-oVVRybJBUFc5RbERl0ZgTObcDrTHTb7Tt-Q/edit#gid=0)



### :bulb: 와이어 프레임

[Dash Board 와이어 프레임 확인하기](https://www.figma.com/file/0ZJIzrgYOiCZTOhIdLfu9P/%ED%92%88%EC%A7%88%EC%8B%9C%EC%8A%A4%ED%85%9C-%EB%8C%80%EC%8B%9C%EB%B3%B4%EB%93%9C-%EA%B0%9C%EB%B0%9C?node-id=0%3A1)



### :bulb: 자유PJT 중간 평가 발표자료

[1차 발표자료 확인하기](https://docs.google.com/presentation/d/1uFvKVPToE4H3NosiXdUk18DUk9smMeLglJyJhcZ-Ndw/edit#slide=id.gfa01fc23d0_0_13)



## 개발



### :bulb: Custom Git Flow

1. `Branch 전략`

참고: https://techblog.woowahan.com/2553/

- main : 최종적으로 배포되는 브랜치
- develop : 다음 출시 버전을 개발하는 브랜치
- feature : 기능을 개발하는 브랜치
- release : 이번 출시 버전을 준비하는 브랜치
- hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치

```shell
main
	└── release
	└── hotfix
	└── develop
         └── feature/front/기능이름
         └── feature/back/기능이름
        		
```



2. `Commit Message Format`

- 모든 커밋 메시지는 다음과 같은 형식을 따른다.

`<type>: <message> (#<issue number>)`

- 예) feat: Add User Login (#S05003-534)
- type은 다음과 같다.

```
types = {      
	feat: 새로운 기능에 대한 커밋      
	fix: 버그 수정에 대한 커밋      
	build: 빌드 관련 파일 수정에 대한 커밋      
	chore: 그 외 자잘한 수정에 대한 커밋      
	ci: CI관련 설정 수정에 대한 커밋      
	docs: 문서 수정에 대한 커밋      
	style: 코드 스타일 혹은 포맷 등에 관한 커밋      
	refactor:  코드 리팩토링에 대한 커밋      
	test: 테스트 코드 수정에 대한 커밋   
}
```
