## 起動方法 ##
  1. https://ddd-wms.googlecode.com/svn/trunk/ddd-wms-emulator をチェックアウト
  1. mvn jetty:run を実行

## 画面（Google Chrome専用） ##
  1. http://localhost:8080/emulator/ にアクセス

## 設定 ##
  * src/main/webapp/WEB-INF/web.xml

> targetURL:
> > エミュレート先ddd-wmsのURL


## 通信フォーマット ##
### 入荷依頼 ###
  * URL

> ${baseUrl}/arrivalRequest/

  * POST
```
{
  "time": 1276665830644,
  "items": [
    { "id": 1, "amount": 5 },
    { "id": 2, "amount": 1 }
  ]
}
```
    * time : 入荷依頼を送信した時刻のタイムスタンプ（？）
    * items : 入荷依頼対象となる商品情報

  * Responce
```
{
  "id": 111
}
```
    * id : 入荷依頼に対してサーバサイドで採番されたID

### 入荷 ###
  * URL
> ${baseUrl}/arrival/

  * POST
```
{
  "id": 111
}
```

  * Responce
    * メッセージなし

### 出荷指示 ###
  * URL
> ${baseUrl}/shippingRequest/

  * POST
```
{
  "time": 1276665830644,
  "items": [
    { "id": 2, "amount": 2 },
    { "id": 4, "amount": 3 }
  ]
}
```
    * time : 出荷を行う時刻（出荷リクエストがあった時点で過ぎているものを出荷する）

  * Responce
```
{
  "id": 111
}
```
    * id : 出荷指示に対してサーバサイドで採番されたID

### 出荷 ###
  * URL
> ${baseUrl}/ship/

  * POST
```
{
  "specifiedShipTime": 1276665830644
}
```
    * specifiedShipTime（出荷指定時刻）：出荷指示のtimeがこの時間以前のものを出荷する。

  * Response
```
{
  "items": [
    { "id": 111 },
    { "id": 222 }
  ]
}
```
    * items : 実行した出荷指示のID


---

## アクションクラス ##
  * ドメインレイヤへのエントリとしてアクションクラスを追加しました。
  * WmsActionクラスの各メソッド内にサービスクラスの呼び出しロジックを実装する想定です。
  * JSON ⇔ Beanの変換にはJSONICを使っています。
  * JSONICのSeasarリポジトリがうまく動かなかったので、コメントアウトしています。
  * 以下のコードでローカルにインストールして下さい。
```
mvn install:install-file -DgroupId=net.arnx.jsonic -DartifactId=jsonic -Dversion=1.2.0 -Dpackaging=jar -DgeneratePom=true -Dfile=/path/to/jarfile.jar
```
  * [JSONIC](http://jsonic.sourceforge.jp/)のサイト