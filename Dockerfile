# 引入openjdk8镜像
FROM gitlab.snz1.cn:2008/devops/java8-springboot-app:1.0

# 设置维护人员信息
MAINTAINER gjh31720@fjtic.cn

# 复制打包好的jar文件到/appm目录
COPY target/smallApp_demo-0.1.0.jar /app/app.jar