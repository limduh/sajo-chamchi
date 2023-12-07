# Team-NBK-04 / sajo-chamchi project
# Team Member

임주리 김성환 임두형 이예원
# 시연 영상
[영상 보기]https://github.com/limduh/nb_01_introduce_myself/assets/111894739/791ea347-8cfa-4f05-ab24-eeb8969eeb1b

# Wireframe

https://www.figma.com/file/MJCpnZA8XTTt3sqUWoEZOT/Untitled?type=design&node-id=0-1&mode=design&t=UReBcifDmTBmzxai-0

![스크린샷 2023-10-05 오후 8 29 27](https://github.com/Team-NBK-04/sajo-chamchi/assets/81704418/28acd35d-a4e6-4a1a-bdbd-551f2b053160)

# Development Environment

- Android Studio
- Kotlin 1.8.0

# Application Version

- SDK version : Android 12
- compileSDK : 33
- minSDK : 26
- targetSDK :33

# Introduction

- 사조참치의 매력이 담긴 동영상 플랫폼 앱.
- 동영상 검색을 통해 본인이 원하는 영상을 찾을 수 있고 검색어 추천이나 영상 보관을 통해 편리하게 관련 영상을 찾거나 나중에 다시 시청해 볼 수 있습니다.

# Description

## Fragment

### Home

- AddCategory를 눌러 카테고리들을 관리할수있는 다이얼로그가 나옴
- 3개의 리사이클러 뷰가 있으며 각 아이템을 눌러 상세 다이얼로그를 볼수있음

### Search

- 검색을 할 수 있는 프래그먼트
- 검색 결과의 경우 각 아이템을 누를 경우 상세 다이얼로그 확인 가능
- 검색어 같은 경우 로컬(Shared Preference)에 저장되어 해당값들을 관리 가능
- Recently Search의 값들을 옆의 세모를 눌러 온 오플 할수있으며 해당값을 누를경우 그값은 가장 앞에 나옴

### MyPage

- 로컬(Room DB)에 저장되있는 값들을 볼수있는 프래그먼트 각 아이템을 누를경우 상세 다이얼로그 확인 가능
- 수정 버튼으로 설명을 수정할수있음 해당값은 로컬(Shared Preference)에 저장

## Dialog

### Categories

- 로컬(Shared Preference)에 저장된 category와 Api에서 가져온 Category를 비교하면서 이값들을 설정가능

### Video Detail

- Video의 상세를 볼수있는 dialog
- 좋아요 기능이 있으며 좋아요를 누를 경우 로컬에 추가

## WebViewActivity

- video detail에서 썸네일을 클릭해 WebViewActivity로 이동
- 해당 내용의 유트브 영상을 웹뷰로 보여줌
