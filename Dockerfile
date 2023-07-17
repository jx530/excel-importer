FROM 20-jdk

COPY target/excel-importer-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ['java', '-jar', 'app.jar']