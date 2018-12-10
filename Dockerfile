# 引入openjdk8镜像
FROM docker.io/openjdk

# 设置维护人员信息
MAINTAINER gjh31720@fjtic.cn

# 复制打包好的jar文件到/appm目录
COPY target/xy_invoice.jar /app/app.jar