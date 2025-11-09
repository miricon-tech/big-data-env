# ----- שלב 1: מוציאים את Spark מהאימג' הרשמי לפי ה-SPARK_IMAGE_TAG -----
ARG SPARK_IMAGE_TAG=3.5.3-scala2.12-java11-python3-ubuntu
FROM apache/spark:${SPARK_IMAGE_TAG} AS sparkdist

# ----- שלב 2: הקונטיינר של Jupyter -----
FROM mambaorg/micromamba:1.5.8-jammy

USER root
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl ca-certificates openjdk-11-jdk-headless bash tini gnupg2 jq \
 && rm -rf /var/lib/apt/lists/*

ENV PYSPARK_PYTHON=python \
    PYSPARK_DRIVER_PYTHON=python \
    HADOOP_CONF_DIR=/etc/hadoop/conf \
    SPARK_CONF_DIR=/etc/hadoop/conf

# סביבת פייתון
COPY --chown=${MAMBA_USER}:${MAMBA_USER} environment.yml /tmp/environment.yml
RUN micromamba env create -f /tmp/environment.yml -y && micromamba clean -a -y
RUN micromamba run -n bigdata python -m ipykernel install \
    --name bigdata --display-name "Python (bigdata)"

# <<< כאן הקסם: מעתיקים את ה-client של Spark מהשלב הראשון >>>
COPY --from=sparkdist /opt/spark /opt/spark
ENV SPARK_HOME=/opt/spark
ENV PATH="${SPARK_HOME}/bin:${PATH}"

# מתקינים ורושמים את קרנל Scala (Toree) שמצביע למאסטר הקיים
RUN micromamba run -n bigdata python -m pip install --no-cache-dir toree==0.5.0 && \
    micromamba run -n bigdata jupyter toree install \
      --sys-prefix \
      --spark_home=/opt/spark \
      --kernel_name=scala-toree \
      --interpreters=Scala \
      --spark_opts="--master spark://spark-master:7077 --conf spark.driver.bindAddress=0.0.0.0 --conf spark.driver.host=jupyter"

WORKDIR /workspace
EXPOSE 8888
CMD ["bash","-lc","micromamba run -n bigdata jupyter lab --ip=0.0.0.0 --port=8888 --allow-root --NotebookApp.token='' --no-browser"]