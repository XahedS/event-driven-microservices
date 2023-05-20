FROM ubuntu:latest

# Update packages and install required dependencies
RUN apt-get update && apt-get install -y wget curl zip unzip

# Set DNS servers (replace with appropriate values if needed)
RUN echo "nameserver 8.8.8.8" > /etc/resolv.conf && \
    echo "nameserver 8.8.4.4" >> /etc/resolv.conf

# Download and install SDKMAN
RUN curl -s "https://get.sdkman.io" | bash

# Source SDKMAN in the bashrc file
RUN echo "source "$HOME/.sdkman/bin/sdkman-init.sh"" >> ~/.bashrc

# Install Java and Spring Boot
#RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && \
#    sdk install java && \
#    sdk install springboot"

RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && \
    sdk install java 11.0.12-open && \
    sdk install springboot 2.3.3.RELEASE"
#(Run these manually when you run the container)
#RUN sdk use java 11.0.12-open
#RUN sdk use springboot 2.3.3.RELEASE
#RUN spring install org.springframework.cloud:spring-cloud-cli:2.2.3.RELEASE
#spring cloud --version
# sdk list java
#inside the container run following command to get the encrypted version of pass 'springCloud_Pwd!'
#by supplying the key 'Demo_Pwd!2020'
#spring encrypt springCloud_Pwd! --key 'Demo_Pwd!2020'
# Create the /app directory
RUN mkdir /app

# Download Spring Cloud CLI JAR file
#RUN wget -O /app/spring-cloud-cli.jar https://repo.spring.io/release/org/springframework/cloud/spring-cloud-cli/3.1.1/spring-cloud-cli-3.1.1.jar

# Set the working directory
WORKDIR /app

# Copy the entrypoint script into the image
#COPY entrypoint.sh /app/entrypoint.sh

# Make the entrypoint script executable
#RUN chmod +x /app/entrypoint.sh

# Start a new shell session with the entrypoint script
#ENTRYPOINT ["/app/entrypoint.sh"]

