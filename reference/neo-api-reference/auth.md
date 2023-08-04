---
description: OAuth2.0를 활용한 인증과 인가와 관련된 엔드포인트를 제공합니다.
---

# 🔐 Auth



{% hint style="info" %}
**Good to know:** All the methods shown below are synced to an example Swagger file URL and are kept up to date automatically with changes to the API.
{% endhint %}

## Login

NEO의 기능을 사용하기 위해, 사용자가 맞는지 확인하는 인증 API입니다.

{% swagger method="get" path="/auth/login" baseUrl="http://localhost/api/v1" summary="NEO Login API" %}
{% swagger-description %}
OAuth2.0을 활용한 NEO의 로그인 API입니다.
{% endswagger-description %}
{% endswagger %}



## Logout

NEO 서비스에 대해 안전하게 로그아웃을 진행합니다.



## Joinus

NEO의 기능을 사용하기 위해, 새로운 사용자로서 가입합니다.

