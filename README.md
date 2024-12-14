# LoginFormSample

[ja] ユーザー名とパスワードを入力して成功・失敗を表示する。
テストを自動化するときとき、認証情報をどのようにして管理すればいい?

[en] Enter your username and password to display success or failure. How should I manage
authentication information when automating tests?

## How to Use

Write the username and password in app/gradle.properties

```app/gradle.properties
TEST_USERNAME="abc"
TEST_PASSWORD="abc123"
```

and Just run the test

