# LoreReplacer
設定した正規表現に合致するloreを別の文字に書き換えるmodです。
# 使い方
マインクラフトのディレクトリ内にLoreReplacerというフォルダが生成されるのでその中のreplacers.jsonを別に場所から持ってきたものに置き換えるか

/addreplacerコマンドで新しく追加します。

/removereplacerコマンドで削除できます。
## 細かい/addreplacerの使い方
/addreplacer <正規表現> <置換先>

が基本形です。

空白は#sに置き換えてください。

正規表現についてはhttps://regexr.com/ を参照してください。

//DELETEを置換先とすることで該当する行を削除することができます。

例1: /addreplacer test#sdayo テスト#sだよ

例2: /addreplacer koreha#siranai#sgyoudesu //DELETE
## 細かい/removereplacerの使い方
基本/addreplacerと同じです。

ただ基本形は/removereplacer <正規表現>となっています。

また実行すると該当するものすべてが削除されます。
## replacers.jsonについて
上記の通りディレクトリ内に生成されます。

json形式のファイルで
```
{
  "replacers": [
    {
      "from": "",
      "to": ""
    }
  }
}
```
のようなもので構成されます。

fromには正規表現を、toには置換先文字列を入力してください。

/addreplacerを使うと自動で書き込まれますが#sなどを使う必要がない分大量の文字列を置換する場合にはこちらを使用したほうが効率的です。
# ライブラリ
このmodはApache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0) のライセンスで配布されている成果物を使用しています。
- Gson
