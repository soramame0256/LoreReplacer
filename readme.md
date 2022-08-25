# LoreReplacer
設定した正規表現に合致するloreを別の文字に書き換えるmodです。
# 使い方
マインクラフトのディレクトリ内にLoreReplacerというフォルダが生成されるのでその中のreplacers.jsonを別に場所から持ってきたものに置き換えるか
/addreplacerコマンドで新しく追加します。
## 細かい/addreplacerの使い方
/addreplacer <正規表現> <置換先>

が基本形です。

空白は#sに置き換えてください。

正規表現についてはhttps://regexr.com/ を参照してください。

//DELETEを置換先とすることで該当する行を削除することができます。

例1: /addreplacer test#sdayo テスト#sだよ

例2: /addreplacer koreha#siranai#sgyoudesu //DELETE
# ライブラリ
このmodはApache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0) のライセンスで配布されている成果物を含んでいます。
- Gson
