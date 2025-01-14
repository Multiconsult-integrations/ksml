FROM redhat/ubi8:8.6-990 as graal-builder
ARG TARGETARCH
ENV JAVA_HOME=/opt/graalvm
ENV PATH=/opt/graalvm/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
USER root
WORKDIR /

# Step 1 Download and install Maven and GraalVM for build and reuse in second stage, cache the results
#  --mount=type=cache,target=/opt/graalvm,id=grl_$TARGETARCH \
RUN \
  --mount=type=cache,target=/downloads/$TARGETARCH,id=grlDownloads_$TARGETARCH \
  JAVA_ARCH= \
  && case "$TARGETARCH" in \
  amd64) \
    JAVA_ARCH="amd64" \
  ;; \
  arm64) \
    JAVA_ARCH="aarch64" \
  ;; \
  *) \
    echo "Unsupported target architecture $TARGETARCH" \
    exit 1 \
  ;; \
  esac  \
  && useradd -m -N -G "users" -u 1024 "graal" \
  && DOWNLOAD_DIR="/downloads/$TARGETARCH" \
  && mkdir -p "${DOWNLOAD_DIR}" \
  && curl -o "/${DOWNLOAD_DIR}/maven.tgz" "https://archive.apache.org/dist/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz" \
  && curl -k -L -o "/${DOWNLOAD_DIR}/graalvm.tgz" "https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-22.3.0/graalvm-ce-java17-linux-${JAVA_ARCH}-22.3.0.tar.gz" \
  && tar -xzf "/${DOWNLOAD_DIR}/maven.tgz" -C "/" \
  && tar -xzf "/${DOWNLOAD_DIR}/graalvm.tgz" -C "/opt" \
  && mv /opt/graalvm* /opt/graalvm \
  && mkdir -p "/opt/ksml/libs" \
  && chown -R graal:users /opt \
  && chown -R graal:users /tmp \
  && /opt/graalvm/bin/gu -A install python

# Step 2 Build the KSML Project, cache the M2 repository location
FROM graal-builder as builder
ADD . /project_dir
RUN \
  --mount=type=cache,target=/root/.m2/repo/$TARGETARCH,id=mvnRepo_$TARGETARCH \
  cd /project_dir \
  && /apache-maven-3.8.5/bin/mvn -Dmaven.repo.local="/root/.m2/repo/$TARGETARCH" dependency:go-offline --no-transfer-progress \
    && /apache-maven-3.8.5/bin/mvn -Dmaven.repo.local="/root/.m2/repo/$TARGETARCH" --no-transfer-progress package

# Step 3 Build the basic graalvm image stage
FROM redhat/ubi8:8.6-990 as ksml-graal
MAINTAINER Axual <maintainer@axual.io>
ENV JAVA_HOME=/opt/graalvm
ENV PATH=/opt/graalvm/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
RUN  useradd -m -N -G "users" -u 1024 "graal"
COPY --chown=graal:users --from=graal-builder /opt/ /opt/

# Step 4 The stage to build KSML runner
FROM ksml-graal as ksml
COPY --chown=graal:users --from=builder /project_dir/ksml-runner/target/libs/ /opt/ksml/libs/
COPY --chown=graal:users --from=builder /project_dir/ksml-runner/target/ksml-runner*.jar /opt/ksml/ksml.jar

WORKDIR /opt/ksml
USER graal:users
ENTRYPOINT ["java", "-jar", "/opt/ksml/ksml.jar"]

# Step 5 The stage to build KSML data generators
FROM ksml-graal as ksml-datagen
COPY --chown=graal:users --from=builder /project_dir/ksml-data-generator/target/libs/ /opt/ksml/libs/
COPY --chown=graal:users --from=builder /project_dir/ksml-data-generator/target/ksml-data-generator-*.jar /opt/ksml/ksml-data-generator.jar

RUN chown -R graal:users /opt
WORKDIR /opt/ksml
USER graal:users
ENTRYPOINT ["java", "-jar", "/opt/ksml/ksml-data-generator.jar"]
