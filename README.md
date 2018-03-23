[![License](http://img.shields.io/:license-apache-blue.svg?style=flat-square)](http://www.apache.org/licenses/LICENSE-2.0.html) 
[![Build Status](https://travis-ci.org/armenak/DataDefender.svg?branch=master)](https://travis-ci.org/armenak/DataDefender)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ed95fbecb60b4e98973c976f338ab4b5)](https://www.codacy.com/app/agstrider/DataDefender?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=armenak/DataDefender&amp;utm_campaign=Badge_Grade)
[![](https://codescene.io/projects/1404/status.svg) Get more details at **codescene.io**.](https://codescene.io/projects/1404/jobs/latest-successful/results)

Data Discovery and Anonymization toolkit
========================================

Table of content
----------------
- [Purpose](#purpose)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Build from source](#build-from-source)
- [Contributing](#contributing)
- [How to run](#how-to-run-data)
- [File Discovery](#file-discovery)
- [Column Discovery](#column-discovery)
- [Data Discovery](#data-discovery)
- [Data Generator](#data-generator)
- [Anonymizer](#anonymizer)
- [Using 3rd-Party JDBC Drivers with Maven](#using-3rd-party-jdbc-drivers-with-maven)
- [Using Maven to execute Anonymizer](#using-maven-to-execute-anonymizer)
	- [Prerequisites](#prerequisites-1)
	- [Configuration](#configuration)
	- [Execution](#execution)
- [Features and issues](#features-and-issues)
- [Code quality](#code-quality)

Purpose
-------
While performing the application development, testing, or maintenance, it is important to operate in the environment that is as close to the production environment as possible when it comes to amount of data and close-to-real content. At the same time it is important to ensure that data privacy policies are not violated. 

Database and file discovery identifies and analyzes data risks. Data anonymization allows to anonymize the sensitive data and transfer information between organizations, while reducing the risk of unintended disclosure. 

The complete source code is available, so you can inspect it and perform security audits if necessary.

This implementation of Data Discovery program is using [Apache OpenNLP](https://opennlp.apache.org/)

Features
--------
1. Identifies sensitive personal data
2. Creates plan (XML document) to define what columns and how should be anonymized
3. Anonymizes the data
4. Platform-independent
5. Supports Oracle, MS SQL Server, and MySQL. Work in progress for DB2 and PostgreSQL.

Prerequisites
----------------
1. JDK 1.8+
2. Maven 3+

Build from source
-----------------
1. Download ZIP file and unzip in a directory of your choice, or clone repo
2. cd {dir}/DataDefender/
3. mvn install
4. DataDefender.jar will be located in "target" directory {dir}/DataDefender/target/

Contributing
------------
We encourage you to contribute to DataDefender! Please check out the [Contribution guidelines for this project](CONTRIBUTING.md). 

How to run
----------
The toolkit is implemented as a command line program. To run it first build the application as above (mvn install). This
will generate an executeable jar file in the "target" directory. Once this has been done you can get help by typing:

    java -jar DataDefender.jar --help
    
The toolkit can be run in anonymizer mode, and three different discovery modes (file, column, and database discovery). For column and database discovery modes you need to provide
the database property file which defines which database to connect to and how to connect. The location of this property file it passed in using the -P or --data switch.

All modes support an optional list of tables at the end to use for either discover, or anonymization of a specific table or list of tables.

File Discovery
--------------
File discovery will attempt to find a sensitive personal information in binary and text files located on the file system.

Sample project can be found here: https://github.com/armenak/DataDefender/tree/master/sample_projects/file_discovery

In order to run File Discovery, please use filediscovery.properties file created in the sample project and adjust it for your needs. Specifically, probability threshold and the directory where files that needs to be scanned will need to be modified.

Column Discovery
----------------
In this mode the tool attempts to query your database and identified columns that should be anonymized based on their names.  When -r is provided a sample requirements file (which can be modified and used the anonymizer stage) will be created based on the columns discovered. To run in this mode type the following:

    java -jar DataDefender.jar database-discovery -c --data <db.properties> --column-discovery <columndiscovery.properties> [-r -R <requirement_output_file>]
    
Where:
    <db.properties>              - Path and file name of the file containing database connection properties 
                                   (see src/main/resources/db.properties for an example)

    <columndiscovery.properties> - Path and file name of the file containing column discovery properties
                                   (see src/main/resources/columndiscovery.properties for an example)
    <requirement_output_file>    - Optional name of sample requirement file to be created (-r must also be specified)
    

Data Discovery
------------------
To run the tool in Data Discovery mode, pass '-d' to discover.  DA will perform an NLP scan of data in the database and return columns that have a match score greater than the value of probability_threshold specified in datadiscovery.properties file.  When -r is provided a sample requirements file (which can be modified and used the anonymizer stage) will be created based on the columns discovered by the DA.

    java -jar DataDefender.jar database-discovery -d --data <db.properties> --data-discovery <datadiscovery.properties> [-r -R <requirement_output_file>]

Where:
    <db.properties>            - Path and file name of the file containing database connection properties 
                                (see src/main/resources/db.properties for an example)

    <datadiscovery.properties> - Path and file name of the file containing data discovery properties
                                (see src/main/resources/datadiscovery.properties for an example)
    <requirement_output_file>  - Optional name of sample requirement file to be created (-r must also be specified)

Data Generator
------------------
The Data Generator is used to load table data into text files.  The text files are useful to modify and then feed into the annoymizer as input data.  The requirements file specified in the anonymizer-properties file define that tables and columns that will be processed.
    java -jar DataDefender.jar generate --data <db.properties> --anonymizer-properties <anonymizer.properties>

Where:
    <db.properties>             - Path and file name of the file containing database connection properties 
                                (see src/main/resources/db.properties for an example)
    <anonymizer.properties>     - Path and file name of the file containing anonymizer properties
                             (see src/main/resources/anonymizer.properties for an example)

Anonymizer
------------------
In this mode, DA will anonymize the data in the database based on the requirements file specified in the anonymizer.properties file.  The requirements file is an XML-formatted file describing which tables and columns should be anonymized, and how.  For an example, refer to src/main/resources/Requirement.xml.

    java -jar DataDefender.jar anonymize --data <db.properties> --anonymizer-properties <anonymizer.properties>

Where:
    <db.properties>         - Path and file name of the file containing database connection properties 
                             (see src/main/resources/db.properties for an example)

    <anonymizer.properties> - Path and file name of the file containing anonymizer properties
                             (see src/main/resources/anonymizer.properties for an example)

To run the anonymizer on specific tables, the table names can be passed at the end:

    java -jar DataDefender.jar anonymize -P db.properties -A anonymizer.properties myTable1 myTable2

This would run the anonymizer for tables defined in the requirements XML file with the name myTable1 or myTable2 only.  Any other tables defined in the requirements would be ignored.


Using 3rd-Party JDBC Drivers with Maven
------------------
Unfortunately, not all JDBC drivers are downloadable via a publicly available maven repostitory and must be downloaded individually.  For example:

- http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html
- http://www.microsoft.com/en-us/download/details.aspx?displaylang=en&id=11774

In order to use these drivers via maven you can add the driver jar to your private maven repository if you have one or install locally:

<ol>
<li>download package</li>
<li>unzip/extract jdbc jar file from package</li>
<li>add driver to your local maven repository by executing:  
<pre>
mvn install:install-file -Dfile=${path to jdbc driver jar file} -DgroupId=${groupId} -DartifactId=${artifactId} -Dversion=${version} -Dpackaging=jar
</pre>
</li> 
<li>add dependency to pom.xml:
<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;${groupId}&lt;/groupId&gt;
        &lt;artifactId&gt;${artifactId}&lt;/artifactId&gt;
        &lt;version&gt;${version}&lt;/version&gt;
    &lt;/dependency&gt;
</pre>
</li>
</ol>


Using Maven to execute Anonymizer
------------------

The pom.xml has also been configured to allow easy execution of the Anonymizer suite of programs from the command line. This option allows you to quickly run any of the programs after you make changes to the code, for example. Most of the configurable options are supported via Maven, but please read on for the details.

<h3>Prerequisites</h3>

Ensure that:
- The required database driver has been installed and configured for Maven (refer to 3rd party JDBC section if necessary).
- 'mvn clean compile' or even better 'mvn clean test' has been run successfully
- Necessary property files (db.properties, and at least one of: columndiscovery|datadiscovery|anonymizer.properties) have been configured and placed under ${execAppDir} (defaults to ${basedir}/exec-app/ directory).

<h3>Configuration</h3>

- ${execAppDir} can be overridden via the command line (ie; -DexecAppDir=blah...) to set the Anonymizer working directory (and also the relative directory where the properties files should be placed).  *Note: the log directory will also be created under ${execAppDir}
- Maven doesn't handle dynamic behaviour very well (such as a variable # of tables names given on the command line); therefore, in order to restrict applications to certain tables, space-deliminted table names must be provided via the optional 'tables' property in the appropriate application properties file (columndiscovery|datadiscovery|anonymizer.properites).

<h3>Execution</h3>

A maven profile has been configured for each application, here are the Maven commands to run for the following programs:
- data generator: mvn exec:exec -P generate
- column discovery: mvn exec:exec -P column-discovery
- data discovery: mvn exec:exec -P data-discovery
- anonymizer: mvn exec:exec -P anonymize

<h3>Features and issues</h3>
Please report issues or ask for future requests here: https://github.com/armenak/DataDefender/issues

<h3>Code quality</h3>
Two amazing tools - Empear http://empear.com/ and SonarQube http://www.sonarqube.org/ help contributors of DataDefender maintain decent quality of code. Many thanks to their creators!
