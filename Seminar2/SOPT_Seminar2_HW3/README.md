# [성장과제1] 네이버 웹툰 뷰 만들기
## Grid RecyclerView


- 그리드리사이클러뷰 사용하기   
https://blog.naver.com/PostView.nhn?blogId=rkswlrbduf&logNo=221200565254&redirect=Dlog&widgetTypeCall=true&directAccess=false

https://thdev.tech/androiddev/2016/11/01/Android-RecyclerView-intro/

https://m.blog.naver.com/PostView.nhn?blogId=tpgns8488&logNo=220983389569&proxyReferer=http:%2F%2Fwww.google.com%2Furl%3Fsa%3Dt%26rct%3Dj%26q%3D%26esrc%3Ds%26source%3Dweb%26cd%3D4%26ved%3D2ahUKEwiEgvLS4J_pAhUCxosBHd0IBFQQFjADegQIBBAB%26url%3Dhttp%253A%252F%252Fm.blog.naver.com%252Ftpgns8488%252F220983389569%26usg%3DAOvVaw1ybHewHtuB7wtdWyjRrZMO

- activity_main.xml에 백그라운드 색깔 gray로 주었다.


추후 리드미 다시 정리해서 수정하기!!



그리드리사이클러뷰를 사용해보는 것이 가장 큰 목표라 다른 부분을 많이 신경쓰지는 못함.

<개선방향> 
1. 뷰 페이저 넣어서 옆으로 슬라이드 가능하게 하기
2. 바텀네비게이션 fragment 만들어서 하단 탭 클릭시 다른 화면이 뜰 수 있게 하기

--------추가사항------------   
3. 하단 아이콘이 4개 이상일 때 아이콘 이미지만 보인다. 아이콘 글자 같이 보이게 하는 법: shift mode를 false로 설정.

4. 그리드레이아웃 이미지 같은 크기로 보여지게 하기
https://webnautes.tistory.com/1299
그리고 이왕이면 itemDecoration 기능 넣어서 각 아이템 주위에 여백 주면 좋을 듯

5. 세부기능 넣기 (좀 더 웹툰창처럼...)
