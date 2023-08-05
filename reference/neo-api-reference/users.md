---
description: >-
  NEO의 사용자와 관련된 API를 제공합니다. 사용자에 대한 검색, 사용자의 정보 수정 및 열람, 구독 정보 등과 관련된 엔드포인트를
  제공합니다.
---

# 🧑🤝🧑 Users

## 🔎 Search

{% swagger baseUrl="http://localhost/api/v1" method="get" path="/users/stars" summary="Search Star API" %}
{% swagger-description %}
NEO에 등록된 스타를 검색하는 API입니다.

만약 Query Parameter인 search\_key가 비어있다면, 모든 스타에 대해 사전 순으로 정렬된 데이터를 획득할 수 있습니다.

모든 데이터는 페이지네이션되어, 한 오프셋에 대해 최대 10개의 데이터를 얻어올 수 있습니다.
{% endswagger-description %}

{% swagger-parameter in="query" name="search_key" required="false" type="String" %}

{% endswagger-parameter %}

{% swagger-parameter in="query" name="offset" type="long" %}

{% endswagger-parameter %}

{% swagger-response status="200" description="Pet successfully created" %}
```javascript
{
    "name"="Wilson",
    "owner": {
        "id": "sha7891bikojbkreuy",
        "name": "Samuel Passet",
    "species": "Dog",}
    "breed": "Golden Retriever",
}
```
{% endswagger-response %}

{% swagger-response status="401" description="Permission denied" %}

{% endswagger-response %}
{% endswagger %}

## Information

{% swagger src="https://petstore.swagger.io/v2/swagger.json" path="/pet" method="put" %}
[https://petstore.swagger.io/v2/swagger.json](https://petstore.swagger.io/v2/swagger.json)
{% endswagger %}
