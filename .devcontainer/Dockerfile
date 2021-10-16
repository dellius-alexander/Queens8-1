# Note: You can use any Debian/Ubuntu based image you want. 
# FROM mcr.microsoft.com/vscode/devcontainers/base:0-buster
FROM ubuntu:latest
# [Option] Use the OSS Moby Engine instead of the licensed Docker Engine
ARG USE_MOBY="true"
# [Option] Install zsh
ARG INSTALL_ZSH="true"
# [Option] Upgrade OS packages to their latest versions
ARG UPGRADE_PACKAGES="false"
# [Option] Enable non-root Docker access in container
ARG ENABLE_NONROOT_DOCKER="true"
# Install needed packages and setup non-root user. Use a separate RUN statement to add your
# own dependencies. A user of "automatic" attempts to reuse an user ID if one already exists.
ARG USERNAME=automatic
ARG USER_UID=1000
ARG USER_GID=$USER_UID
ARG JAVA_VERSION=""
ARG SDKMAN_DIR=""

# GIT User Info
ARG GIT_USER=""
ARG GIT_EMAIL=""
# [Option] Install Maven
ARG INSTALL_MAVEN=""
ARG MAVEN_VERSION=""
# [Option] Install Gradle
ARG INSTALL_GRADLE=""
ARG GRADLE_VERSION=""
# [Choice] Node.js version: none, lts/*, 16, 14, 12, 10
ARG NODE_VERSION=""
# Workspace/App directory
ARG APP_DIR=""
# Git repo
ARG GIT_REPO=""
# Git Branch
ARG GIT_BRANCH=""
# User Passwd
ARG USERCRED=""
# Time Zone
ARG TZ=""
#####################################################################
# Add project directory to Environment variables
ENV APP_DIR=${APP_DIR}
WORKDIR ${APP_DIR}
ENV TZ=${TZ}
RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime \
# && dpkg-reconfigure -f noninteractive tzdata \
&& echo ${TZ} > /etc/timezone
#####################################################################
# Install initial packages: git, apt-utils, zsh base, openjdk
RUN apt-get update -y \
    && apt-get install -y \
    git \
    apt-utils 2>&1 \
    zsh \
    openjdk-${JAVA_VERSION}-jdk
# Copy scripts
COPY library-scripts/** /tmp/library-scripts/
# COPY packages/* /tmp/packages/
# RUN mv /tmp/packages/sdkmanager /usr/local/bin/
# ENV SDKMANAGER=/usr/local/bin/sdkmanager

# Setup user environment
RUN apt-get update \
    && /bin/bash /tmp/library-scripts/common-debian.sh "${INSTALL_ZSH}" "${USERNAME}" "${USER_UID}" "${USER_GID}" "${UPGRADE_PACKAGES}" "true" "true" \
    # Use Docker script from script library to set things up
    && /bin/bash /tmp/library-scripts/docker-in-docker-debian.sh "${ENABLE_NONROOT_DOCKER}" "${USERNAME}" "${USE_MOBY}"
# Install oh-my-zsh
RUN rm -rf ~/.oh-my-zsh && sh -c "$(curl -fsSL https://raw.github.com/ohmyzsh/ohmyzsh/master/tools/install.sh)" "Y"
# Install sdk manager
# RUN curl -s "https://get.sdkman.io" | bash 
# && . "/root/.sdkman/bin/sdkman-init.sh"
# && sdk help
# Install maven
# RUN bash  /tmp/library-scripts/java-debian.sh  \
# "${JAVA_VERSION}" "${SDKMAN_DIR}" "${USERNAME}" "true"
# Install packages and setup configurations from script below
RUN bash  /tmp/library-scripts/maven-debian.sh \
    && mvn -v
RUN bash  /tmp/library-scripts/user.sh
# RUN if [ "${INSTALL_MAVEN}" == "true" ]; then su vscode -c "umask 0002 \
# && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""; fi 
# RUN if [ "${INSTALL_GRADLE}" == "true" ]; then su vscode -c "umask 0002 \
# && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""; fi
# RUN if [ "${NODE_VERSION}" != "none" ]; then su vscode -c "umask 0002 \
# && . /usr/local/share/nvm/nvm.sh && nvm install ${NODE_VERSION} 2>&1"; fi

# Create a volume for docker
VOLUME [ "/var/lib/docker" ]
# [Optional] Uncomment this section to install additional OS packages.
# RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
#     && apt-get -y install --no-install-recommends <your-package-list-here>
# Setup Project Directory
RUN mkdir -p ${APP_DIR} \
    && sudo chown -R ${USERNAME}:${USERNAME} ${APP_DIR}
# Clean up
RUN apt-get autoremove -y \
    && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*  /tmp/library-scripts/
# Setting the ENTRYPOINT to docker-init.sh will start up the Docker Engine 
# inside the container "overrideCommand": false is set in devcontainer.json. 
# The script will also execute CMD if you need to alter startup behaviors.
ENTRYPOINT [ "/usr/local/share/docker-init.sh" ]
CMD [ "sleep", "infinity" ]
