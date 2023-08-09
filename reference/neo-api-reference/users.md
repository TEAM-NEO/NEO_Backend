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

***

## 📄 Information



{% swagger method="get" path="/users/{user_id}" baseUrl="https://localhost/api/v1" summary="Get User Info API" %}
{% swagger-description %}
Whether you are a star or a fan, you can get user information through this API with PathParameter "user\_id".


{% endswagger-description %}

{% swagger-parameter in="path" name="user_id" required="true" %}
Neo User ID
{% endswagger-parameter %}
{% endswagger %}

{% swagger method="patch" path="/users/{user_id}" baseUrl="https://localhost/api/v1" summary="Change Partial Information API" %}
{% swagger-description %}
You can change your partial information by body parameter. Only you can contact your data. Because of PATCH Http Method, only what is contained in the body parameter is modified and the rest of the data is preserved.
{% endswagger-description %}

{% swagger-parameter in="path" name="user_id" required="true" %}
Neo User ID
{% endswagger-parameter %}

{% swagger-parameter in="body" %}

{% endswagger-parameter %}
{% endswagger %}



{% swagger method="put" path="/users/{user_id}" baseUrl="https://localhost/api/v1" summary="Change Information API" %}
{% swagger-description %}
You can change your information by body parameter. Only you can contact your data. Because of PUT Http Method, note that data not included in the body will be null.
{% endswagger-description %}

{% swagger-parameter in="path" name="user_id" required="true" %}
Neo User ID
{% endswagger-parameter %}
{% endswagger %}



{% swagger method="post" path="/users/stars" baseUrl="https://localhost/api/v1" summary="Create User as Star and create Information API" %}
{% swagger-description %}
After register in Neo, make new information through this api. Only star use this api.
{% endswagger-description %}
{% endswagger %}



{% swagger method="post" path="/users/fans" baseUrl="https://localhost/api/v1" summary="Create User as Fan and create Information API" %}
{% swagger-description %}
After register in Neo, make new information through this api. Only fan use this api.
{% endswagger-description %}
{% endswagger %}

{% swagger method="delete" path="/users/{user_id}" baseUrl="https://localhost/api/v1" summary="Delete User Information API" %}
{% swagger-description %}
This API is only for star. You can delete all star's custom information through this API.
{% endswagger-description %}
{% endswagger %}

***

## 💜 Follower(My fans)

{% swagger method="get" path="/users/{user_id}/followers" baseUrl="https://localhost/api/v1" summary="Get Followers(My fans) List API" %}
{% swagger-description %}
You can get followers list by this api. Only star can use this api, because fan can't have follower. You can get whole follower's count through query parameter 'count'. We give you list with pagination, so if you want next page, consider query parameter 'offset'.
{% endswagger-description %}

{% swagger-parameter in="query" name="count" type="boolean" %}
contain count data
{% endswagger-parameter %}

{% swagger-parameter in="query" name="offset" type="long" %}
followers page offset
{% endswagger-parameter %}
{% endswagger %}



{% swagger method="delete" path="/users/{user_id}/followers" baseUrl="https://localhost/api/v1" summary="Block fan API" %}
{% swagger-description %}
You can block fan through this api.
{% endswagger-description %}
{% endswagger %}

***

## 💜 Followee(My Star)

{% swagger method="get" path="/users/{user_id}/followee" baseUrl="https://localhost/api/v1" summary="Get My Stars List API" %}
{% swagger-description %}
You can get your stars list through this API. Whether you are a star or not, you can use this API. 
{% endswagger-description %}

{% swagger-parameter in="query" name="count" type="boolean" %}
contain count or not
{% endswagger-parameter %}

{% swagger-parameter in="query" name="offset" type="long" %}
followee page offset
{% endswagger-parameter %}
{% endswagger %}



{% swagger method="post" path="/users/{user_id}/followee" baseUrl="https://localhost/api/v1" summary="Follow Star API" %}
{% swagger-description %}
You can follow star through this API. Whether you are a star or not, you can use this API.
{% endswagger-description %}
{% endswagger %}



{% swagger method="delete" path="/users/{user_id}/followee" baseUrl="https://localhost/api/v1" summary="Unfollow Star API" %}
{% swagger-description %}
You can unfollow star through this API. 
{% endswagger-description %}
{% endswagger %}
