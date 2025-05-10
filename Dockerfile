# Sử dụng base image cho Java
FROM maven:3.8.6-ibmjava-8 AS build

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép mã nguồn vào image
COPY . .

# Chạy maven build
RUN mvn clean package

# Tạo image mới với JDK 17
FROM openjdk:11-jdk-alpine

# Sao chép kết quả build từ bước trước
COPY --from=build /app/target/*.jar /app/app.jar

# Cấu hình lệnh chạy mặc định
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
