24-03-06 ~ 24-03-18

## 요구사항
노트(일지)를 입력하면 원하는 테이블에 각각 저장할수 있게 자료구조를 반환

## 기능
regex 로 추출한 문자를 hashMap에 저장<br>
문자열을 전송하면 문자들을 구분하여 반환

### 예제)
[[1번축사]] 송아지 한마리 탈출<br>
key : 1번축사<br> value : 송아지 한마리 탈출

![image](https://github.com/Yongtapark/NoteParserV0/assets/91367204/5f1a2dfd-9fc5-4417-83b8-4b16342e8905)
![image](https://github.com/Yongtapark/NoteParserV0/assets/91367204/2a83f272-b4a6-4662-8dbb-710c50b6c028)



### 테스트케이스
1. 단일 key 입력 시 ->  &nbsp; &nbsp; &nbsp;[[1번축사]] 오늘은 배가 고프다
2. 잘못된 key 형식 입력 시 ->  &nbsp; &nbsp; &nbsp;[[1번축사,]] 오늘은 배가 고프다
3. 복수 key 입력 시 ->  &nbsp; &nbsp; &nbsp;[[1번축사,2번축사]] 오늘은 배가 고프다



