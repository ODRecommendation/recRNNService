# RNN based recommendation service using BigDL, Mleap, and Play
A example on how to serve rnn recommendation model trained by BigDL and Spark using BigDL, Mleap, and Play

## Requirements
```scala
scalaVersion := "2.11.12"

val jacksonVersion = "2.6.7"
val sparkVersion = "2.3.1"
val analyticsZooVersion = "0.3.0"

libraryDependencies += guice
libraryDependencies += "com.intel.analytics.zoo" % "analytics-zoo-bigdl_0.7.1-spark_2.3.1" % analyticsZooVersion
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.354"
libraryDependencies += "ml.combust.mleap" %% "mleap-spark" % "0.12.0"
libraryDependencies += "ml.combust.mleap" %% "mleap-spark-extension" % "0.12.0"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion
)

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion
dependencyOverrides += "com.google.guava" % "guava" % "17.0"
```

## How to run
Use terminal
```bash
sbt runProd
``` 
you need brew install sbt if necessary.

Package code to one jar and run directly using the java command
```scala
sbt -J-Xmx2G assembly
java -Xmx2g -jar ${location of assembled jar}
```

## API Introduction
you can use postman to send request

### Check health
**Path:** /`healthcheck`  
**Methods:** GET  
**Params:**  
None  
**Return:**
```json
{"status": "ok"}
```

### Check model version
**Path:** /`versioncheck`  
**Methods:** GET  
**Params:**  
None  
**Return:**
```json
{
    "status": "ok",
    "recModelVersion": "2019-01-04 13:22:01.000",
    "skuIndexerModelVersion": "2019-01-04 12:49:02.000"
}
```

### Run recommendation model
**Path:** /`recModel`  
**Methods:** POST  
**Params:**  
```json
{
    "SESSION_ID": "12345",
    "SKU_NUM": ["798644", "799238", "8284111"]
}
``` 
**Return:**
```json
[
    {
        "predict1": "795756",
        "probability1": 0.00000834272707940318
    },
    {
        "predict2": "870284",
        "probability2": 6.271142819772266e-11
    },
    {
        "predict3": "798878",
        "probability3": 0.00001927549222868919
    },
    {
        "predict4": "803342",
        "probability4": 4.941155854872043e-7
    },
    {
        "predict5": "798284",
        "probability5": 3.745753121038174e-7
    }
]
```  
**Error_message:** ""Nah nah nah nah nah...this request contains bad characters...""

## Contact & Feedback

 If you encounter bugs, feel free to submit an issue or pull request.
 Also you can mail to:
 * Luyang, Wang (tmacraft@hotmail.com)


