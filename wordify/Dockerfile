# ベースイメージとしてTomcat 9を使用
FROM tomcat:10-jdk17-corretto

# アプリケーションのWARファイルをTomcatのWebアプリケーションディレクトリにコピー
COPY ./target/wordify-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

# Tomcatコンテナのポートを公開（デフォルトは8080）
EXPOSE 8081

# コンテナ起動時にTomcatを実行
CMD ["catalina.sh", "run"]
