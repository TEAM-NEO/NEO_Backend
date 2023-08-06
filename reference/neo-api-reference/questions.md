---
description: NEO의 스타가 팬에게 물어보는 Q&A 기능과 관련된 API를 제공합니다.
---

# ❓ Questions

## Questions

{% swagger method="post" path="/questions" baseUrl="https://localhost/api/v1" summary="Create new question API" %}
{% swagger-description %}

{% endswagger-description %}
{% endswagger %}



{% swagger method="get" path="/questions" baseUrl="https://localhost/api/v1" summary="Get Questions List API" %}
{% swagger-description %}
**For Star**



**For Fans**
{% endswagger-description %}
{% endswagger %}



{% swagger method="get" path="/questions/{question_id}" baseUrl="https://localhost/api/v1" summary="Get one question API" %}
{% swagger-description %}
You can get 
{% endswagger-description %}
{% endswagger %}



## Reply

{% swagger method="post" path="/questions/{question_id}/replies" baseUrl="https://localhost/api/v1" summary="Create new reply API" %}
{% swagger-description %}

{% endswagger-description %}
{% endswagger %}



{% swagger method="get" path="/questions/{question_id}/replies" baseUrl="https://localhost/api/v1" summary="Get Replies API" %}
{% swagger-description %}

{% endswagger-description %}
{% endswagger %}



## Supported

{% swagger method="post" path="/questions/{question_id}/replies/supported" baseUrl="https://localhost/api/v1" summary="Support reply API" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="query" name="in_reply" type="long" required="true" %}
reply id
{% endswagger-parameter %}
{% endswagger %}



{% swagger method="delete" path="/questions/{question_id}/replies/supported" baseUrl="https://localhost/api/v1" summary="Unsupport reply API" %}
{% swagger-description %}

{% endswagger-description %}

{% swagger-parameter in="query" name="in_reply" type="long" required="true" %}
reply id
{% endswagger-parameter %}
{% endswagger %}
