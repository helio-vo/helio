 java -classpath stilts.jar:mysql-connector-java-5.0.6-bin.jar \
        -Djdbc.drivers=com.mysql.jdbc.Driver \
        uk.ac.starlink.ttools.Stilts tcopy \
        in="jdbc:mysql://msslxt.mssl.ucl.ac.uk/helio_ics?user=helio_select&password=helio_select#SELECT * FROM instruments limit 10" \
        ofmt=votable out=instr2.vot
