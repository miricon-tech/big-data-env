# ----- שלב 1: Spark base -----
ARG SPARK_IMAGE_TAG=3.5.3-scala2.12-java11-python3-ubuntu
FROM apache/spark:${SPARK_IMAGE_TAG} AS sparkdist

# ----- שלב 2: Jupyter עם Micromamba -----
FROM mambaorg/micromamba:1.5.8-jammy

USER root

# 🛠️ Build tools כדי להתקין phik + wordcloud
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl ca-certificates openjdk-11-jdk-headless bash tini gnupg2 jq \
    build-essential python3-dev cmake libjpeg-dev zlib1g-dev libpng-dev \
    libfreetype6-dev pkg-config \
 && rm -rf /var/lib/apt/lists/*

# Spark/Hadoop config
ENV HADOOP_CONF_DIR=/etc/hadoop/conf \
    SPARK_CONF_DIR=/etc/hadoop/conf

# ✅ יוצרים את סביבה bigdata
COPY --chown=${MAMBA_USER}:${MAMBA_USER} environment.yml /tmp/environment.yml
RUN micromamba env create -n bigdata -f /tmp/environment.yml -y && \
    micromamba clean -a -y

# 🛠️ התקנת wordcloud + phik בנפרד (עם כלים זמינים)
RUN micromamba run -n bigdata pip install --no-cache-dir phik wordcloud

# רישום Kernel
RUN micromamba run -n bigdata python -m ipykernel install \
    --name bigdata --display-name "Python (bigdata)"

# תיקיית עבודה + הרשאות
RUN mkdir -p /workspace && chown -R ${MAMBA_USER}:${MAMBA_USER} /workspace

# ✅ symlink ל־python3 חייב להתבצע כ־root
RUN ln -sf /opt/conda/envs/bigdata/bin/python /usr/bin/python3

# <<< Spark client
COPY --from=sparkdist /opt/spark /opt/spark
ENV SPARK_HOME=/opt/spark
ENV PATH="${SPARK_HOME}/bin:${PATH}"

# Scala (Toree) kernel
RUN micromamba run -n bigdata python -m pip install --no-cache-dir toree==0.5.0 && \
    micromamba run -n bigdata jupyter toree install \
      --sys-prefix \
      --spark_home=/opt/spark \
      --kernel_name=scala-toree \
      --interpreters=Scala \
      --spark_opts="--master spark://spark-master:7077 --conf spark.driver.bindAddress=0.0.0.0 --conf spark.driver.host=jupyter"

# ניקוי
RUN rm -rf /tmp/environment.yml

# 🔄 רק עכשיו לעבור למשתמש רגיל
USER ${MAMBA_USER}

WORKDIR /workspace
EXPOSE 8888

CMD ["bash", "-lc", "micromamba run -n bigdata jupyter lab --ip=0.0.0.0 --port=8888 --allow-root --NotebookApp.token='' --no-browser"]