## 지하철 노선도 미션

## 기능 요구 사항

- 지하철역 사이의 연결 정보인 지하철 구간을 도출하고 이를 관리하는 API를 만들기
- [API 문서v2](https://techcourse-storage.s3.ap-northeast-2.amazonaws.com/c682be69ae4e412c9e3905a59ef7b7ed)
- 완성 후 [프론트엔드 페이지](https://d2owgqwkhzq0my.cloudfront.net/index.html)를 통해 기능 동작 여부 확인하기

### 지하철역

- 지하철역 등록 : POST /stations
    - [x] 요청 성공시, 상태코드 201을 반환한다.
    - [x] 중복되는 이름의 지하철역이 존재하는 경우, 상태코드 400을 반환한다.
    - [x] 이름 혹은 색상 정보가 공백이거나 누락된 경우, 상태코드 400을 반환한다.
- 지하철역 목록 : GET /stations
    - [x] 요청 성공시, 상태코드 200을 반환한다.
- 지하철역 삭제 : DELETE /stations/:id
    - [x] 요청 성공시, 상태코드 204를 반환한다.
    - [x] 삭제하려는 지하철역이 존재하지 않는 경우, 상태코드 404를 반환한다.
    - [x] 노선에 구간으로 등록된 지하철역을 삭제하려는 경우, 상태코드 400을 반환한다.

### 지하철 노선

- 지하철 노선 등록 : POST /lines
    - [x] 요청 성공시, 상태코드 201을 반환한다.
    - [x] 중복되는 이름의 지하철 노선이 존재하는 경우, 상태코드 400을 반환한다.
    - [x] 이름, 색상, 거리, 상행 및 하행 종점 정보 중 하나라도 공백이거나 누락된 경우, 상태코드 400을 반환한다.
    - [x] 존재하지 않는 상행 종점, 하행 종점을 입력한 경우, 상태코드 404를 반환한다.
- 지하철 노선 목록 : GET /lines
    - [x] 요청 성공시, 상태코드 200을 반환한다.
- 지하철 노선 조회 : GET /lines/:id
    - [x] 요청 성공시, 상태코드 200을 반환한다.
    - [x] 조회되는 노선의 구간들은 상행 종점부터 하행 종점까지 정렬되어 조회된다.
    - [x] 조회하려는 지하철 노선이 존재하지 않는 경우, 상태코드 404를 반환한다.
- 지하철 노선 수정 : PUT /lines/:id
    - [x] 요청 성공시, 상태코드 200을 반환한다.
    - [x] 수정하려는 지하철 노선이 존재하지 않는 경우, 상태코드 404를 반환한다.
    - [x] 이미 존재하는 지하철 노선 이름으로 수정하려는 경우, 상태코드 400을 반환한다.
    - [x] 이름 혹은 색상 정보 중 하나라도 공백이거나 누락된 경우, 상태코드 400을 반환한다.
- 지하철 노선 삭제 : DELETE /lines/:id
    - [x] 요청 성공시, 상태코드 204를 반환한다.
    - [x] 해당 노선에 등록된 모든 구간을 삭제한다.
    - [x] 삭제하려는 지하철 노선이 존재하지 않는 경우, 상태코드 404를 반환한다.

### 지하철 구간

- 지하철 구간 등록 : POST /lines/:id/sections
    - [x] 요청 성공시, 상태코드 200을 반환한다.
    - [x] 존재하지 않는 지하철역을 입력한 경우, 상태코드 404를 반환한다.
    - [x] 상행역과 하행역 모두 등록하려는 노선에 포함되어있지 않은 경우, 상태코드 400을 반환한다.
    - [x] 이미 노선에 등록된 지하철역들을 구간으로 재등록하려는 경우, 상태코드 400을 반환한다.
    - [x] 두 역 사이에 새로운 역을 등록하려는 경우, 기존 역 사이의 길이보다 크거나 같으면, 상태코드 400을 반환한다.
- 지하철 구간 삭제 : DELETE /lines/:id/sections?stationId={stationId}
    - [x] 요청 성공시, 상태코드 200를 반환한다.
    - [x] 입력한 노선 혹은 지하철역이 존재하지 않는 경우, 상태코드 404를 반환한다.
    - [x] 노선에 등록되어 있지 않은 지하철역을 입력한 경우, 상태코드 400을 반환한다.
    - [x] 구간이 하나인 노선에서 마지막 구간을 제거하려는 경우, 상태코드 400을 반환한다.


### 지하철 경로

- 지하철 경로 조회 : GET /paths?source={source}&target={target}&age={age}
    - [ ] 요청 성공시, 상태코드 200을 반환한다.
    - [ ] 연결되지 않은 지하철역들 사이의 경로를 조회하려는 경우 상태코드 400을 반환한다.
    - [ ] 존재하지 않는 지하철역이 입력된 경우 상태코드 400을 반환한다.
