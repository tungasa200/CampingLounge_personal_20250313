2025 02 26 수정내역

기존 코드의 문제점

1. 비효율적인 구조
- 작업 중 리뷰 게시물에 라디오 버튼으로 만족도를 체크하는 5가지 항목을 추가 하게 됨.
- 5가지 항목을 리뷰 게시물과 따로 분리하여 Entity, DTO, Repository를 추가 생성.
- 때문에 작성, 조회, 삭제 시 단순 Integer 데이터 몇가지를 조작하기 위해 복잡한 코드를 추가로 작성하게 됨. 

2. DTO의 잘못된 사용
- Request용 DTO와 Response용 DTO를 분리해두었으나 ReviewWriteDto.java 파일을 보면 알 수 있듯 하나의 DTO에 fromEntity 매서드와 toEntity 매서드를 선언하여 혼용.
- ResResponseDto.java에 필요한 정보를 매핑하지 않아 게시물에 조회되어야할 정보 일부 누락.

상기한 문제점 외에도 전반적으로 코드가 효율적이지 못하고 의도대로 작동하지 않던 문제가 다수 발견됨.


수정 내역

1. ReviewSurvey 삭제
- 특별한 이유없이 Integer 필드 몇가지를 위해 따로 분리 되어있던 구조를 개선하기 위해 ReviewSurvey에 관련된 부분을 모두 삭제하고 Review Entity에 필드로 선언. 이 과정에서 Service 레이어와 Repository 레이어가 상당 부분 개선됨.
2. DTO의 역할 분리
- Request DTO와 Response DTO에서 필요한 데이터가 달라 이를 명확하게 분리.
- Requset의 fromEntity를 제거하고 프론트에서 입력하는 정보를 받을 수 있게 수정.
- Response에 fromEntity를 추가하고 게시물 조회시 필요한 정보를 조회할 수 있도록 수정.
3. 전반적인 코드 개선
- 조회수를 카운트하는 매서드를 Service에 직접 입력하던 방식에서 Entity에 매서드를 작성해두고 필요한 곳에서 선언하도록 리팩토링 등 전반적으로 코드를 개선 함.




